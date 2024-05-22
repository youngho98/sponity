<template>
    <div class="container">
      <div id="map"></div>
      <div class="controls">
        <button @click="displayMarkers(searchList)">추천 마커 표시</button>
        <button @click="initMap">내위치</button>
        <!-- <button @click="clearMarkers">추천 마커 해제</button> -->
      </div>
      <br>
      <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>장소명</th>
                <th>추천 이유</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="item in searchList" :key="item[0]">
                <td>{{ item[0] }}</td>
                <td>{{ item[4] }}</td>
            </tr>
            </tbody>
        </table>
      </div>
    </div>
  </template>
  
  
<script setup>
import { onMounted, ref, toRaw } from 'vue';
import { useChatgptStore } from '@/stores/chatgpt';

const chatgptStore = useChatgptStore();
const searchList = chatgptStore.searchList;

let map = null;




const initMap = function () {
let myCenter = new kakao.maps.LatLng(36.355366, 127.298406); // 대전 싸피

if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition((position) => {
    const lat = position.coords.latitude;
    const lon = position.coords.longitude;
    myCenter = new kakao.maps.LatLng(lat, lon);
    new kakao.maps.Marker({
        map,
        position: myCenter,
    });
    map.setCenter(myCenter);
    });
}

const container = document.getElementById('map');
const options = {
    center: myCenter,
    level: 7,
};

// 지도 객체를 등록합니다.
map = new kakao.maps.Map(container, options);

// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
const mapTypeControl = new kakao.maps.MapTypeControl();
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는 줌 컨트롤을 생성합니다
const zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
};


onMounted(() => {
if (window.kakao && window.kakao.maps) {
    initMap();
    displayMarkers(searchList);
} else {
    const script = document.createElement('script'); // autoload=false 스크립트를 동적으로 로드하기 위해서 사용
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_API_KEY}`;

    script.addEventListener('load', () => {
    kakao.maps.load(() => {
        initMap();
        displayMarkers(searchList);
    });
    }); //헤드태그에 추가
    document.head.appendChild(script);
}
});


// 마커 배열
const markers = ref([]);

const displayMarkers = function (locations) {
    // 기존에 있던 마커 지우기
    clearMarkers();

    const positions = locations.map(location => new kakao.maps.LatLng(location[1], location[2]));



    if (positions.length > 0) {
        markers.value = positions.map((position, index) => {
            const marker = new kakao.maps.Marker({
                map: toRaw(map),
                position,
            });

            const iwContent = `<div style="padding:5px;">
                <div style="color: blue; font-size: 17px">
                    ${locations[index][0]}
                </div>
                <div>
                    ${locations[index][3]} &nbsp; &nbsp;
                </div>
                </div>`;

            const infowindow = new kakao.maps.InfoWindow({
                content: iwContent,
            });

            kakao.maps.event.addListener(marker, 'click', () => {
                infowindow.open(map, marker);
            });

            // 화면 로드 직후 인포윈도우 표시하기
            infowindow.open(map, marker)

            return marker;
        });

        const bounds = positions.reduce(
            (bounds, latlng) => bounds.extend(latlng),
            new kakao.maps.LatLngBounds()
        );

        toRaw(map).setBounds(bounds);
    }
};



const clearMarkers = function () {
if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
    markers.value = [];
}
};
</script>


<style scoped>
/* 전체 컨테이너 */
.container {
display: flex;
flex-direction: column;
align-items: center;
justify-content: center;
height: 100vh;
}

/* 지도 컨테이너 */
#map {
width: 60%;
height: 800px;
margin-top: 20px;
margin-bottom: 10px;
border: 2px solid #ccc;
border-radius: 10px;
}

/* 버튼 컨테이너 */
.controls {
display: flex;
gap: 10px;
}

/* 버튼 스타일 */
button {
padding: 7px 10px;
font-size: 16px;
border: none;
border-radius: 5px;
background-color: #a5ef44;
color: black;
cursor: pointer;
}

button:hover {
background-color: #9dce09;
}

/* 테이블 컨테이너 */
.table-container {
  width: 60%;
  padding: 10px;
  margin-top: 10px; 
  margin-bottom: 50px;
}

/* 테이블 스타일 */
table {
  width: 100%;
  border-radius: 10px;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ccc;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f4f4f4;
}
</style>
