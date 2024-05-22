<template>

  <!-- 내비게이션 바 -->
  <!-- <nav class="navbar navbar-expand-lg fixed-top">
    <div class="container">

      <a class="navbar-brand" href="index.html">S P O N I T Y</a>

      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-lg-auto">
          <li class="nav-item">
            <a href="#home" class="nav-link smoothScroll">Home</a>
          </li>

          <li class="nav-item">
            <a href="#about" class="nav-link smoothScroll">클럽 검색</a>
          </li>

          <li class="nav-item">
            <a href="#class" class="nav-link smoothScroll">클럽 생성</a>
          </li>

          <li class="nav-item">
            <a href="#schedule" class="nav-link smoothScroll">추천! with <b>ChatGPT</b></a>
          </li>

          <li class="nav-item">
            <a href="#contact" class="nav-link smoothScroll">Contact</a>
          </li>
        </ul>

        <ul class="social-icon ml-lg-3">
          <li><a href="https://fb.com/tooplate" class="fa fa-facebook"></a></li>
          <li><a href="#" class="fa fa-twitter"></a></li>
          <li><a href="#" class="fa fa-instagram"></a></li>
        </ul>
      </div>

    </div>
  </nav> -->


  <!-- 첫 화면 -->
  <section class="hero d-flex flex-column justify-content-center align-items-center" id="home">

    <div class="bg-overlay"></div>

    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto col-12">
          <div class="hero-text mt-5 text-center">

            <h6 data-aos="fade-up" data-aos-delay="300" class="text-xl font-light">같이,&nbsp;&nbsp;&nbsp;즐겁게,&nbsp;&nbsp;&nbsp;새롭게</h6>

            <h1 class="text-white text-5xl font-extrabold my-10" data-aos="fade-up" data-aos-delay="500">S P O N I T Y</h1>

            <h6 data-aos="fade-up" data-aos-delay="300" class="text-xl font-light">지금 바로 함께 운동할 동네 친구를 찾아보세요.</h6>
            <br>
            <RouterLink :to="{ name: 'loginForm' }" class="btn btn-outline-success mt-3" data-aos="fade-up" data-aos-delay="700">로그인</RouterLink>

          </div>
        </div>
      </div>
    </div>
  </section>


  <!-- "같이" 영역 -->
  <div style="background-color: #171819;">
  <section class="feature mx-auto w-2/3" id="feature">
    <div class="container">
      <div class="row align-items-center">
        <!-- 문구 영역 -->
        <div class="col-lg-6 col-md-6 col-12 d-flex flex-column justify-content-center" data-aos="fade-up">
          <h2 class="mb-3 text-white text-4xl font-bold">같이</h2>
          <h6 class="mb-4 text-white text-xl">집 근처에서 함께 운동할 친구, 어디서 찾지?</h6>
          <p data-aos="fade-up" data-aos-delay="200">
            스포니티는 이러한 물음에서 출발한 "지역기반 운동 클럽 커뮤니티 서비스"입니다.<br>
            하고 싶은 운동을 동네 친구와 함께 즐겨 보세요.
          </p>
          <div style="display: flex; justify-content: space-between;">
            <RouterLink :to="{ name: 'createClubForm' }" class="btn custom-btn bg-color mt-3" data-aos="fade-up" data-aos-delay="300" data-toggle="modal"
              data-target="#membershipForm" style="flex: 1; margin-right: 5px;">클럽 새로 만들기</RouterLink>
            <RouterLink :to="{ name: 'searchClubForm' }" class="btn custom-btn bg-color mt-3" data-aos="fade-up" data-aos-delay="300" data-toggle="modal"
              data-target="#membershipForm" style="flex: 1; margin-left: 5px;">클럽 검색</RouterLink>
          </div>
        </div>

        <!-- 이미지 카드 1번 영역 -->
        <div class="col-lg-3 col-md-3 col-12" data-aos="fade-up" data-aos-delay="700" style="padding: 10px;">
          <div class="team-thumb" style="text-align: center;">
            <img src="@/assets/images/team/team-image.jpg" class="img-fluid" alt="Trainer" style="width: 80%; height: auto;">
            <div class="team-info d-flex flex-column" style="width: 80%;">
              <h3 class="text-3xl font-bold">강해린</h3>
              <br>
              <div>대전광역시 유성구</div>
              <div>러닝, 축구, 배드민턴</div>
            </div>
          </div>
        </div>

        <!-- 이미지 카드 2번 영역 -->
        <div class="col-lg-3 col-md-3 col-12" data-aos="fade-up" data-aos-delay="800" style="padding: 10px;">
          <div class="team-thumb" style="text-align: center;">
            <img src="@/assets/images/team/team-image01.jpg" class="img-fluid" alt="Trainer" style="width: 80%; height: auto;">
            <div class="team-info d-flex flex-column" style="width: 80%;">
              <h3 class="text-3xl font-bold">김민지</h3>
              <br>
              <div>서울특별시 동대문구</div>
              <div>볼링, 테니스, 등산</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  </div>


  <!-- "즐겁게" 영역 -->
  <section class="class section mx-auto w-2/3" id="class">
    <div class="container">
      <div class="row">

        <div class="col-lg-12 col-12 text-center mb-5">
          <h2 data-aos="fade-up" data-aos-delay="200" class="text-4xl font-bold">즐겁게</h2>
          <br>
          <h6 data-aos="fade-up" class="text-xl">14가지 다양한 운동 카테고리.&nbsp;&nbsp;운동 후 즐거운 친목은 덤.</h6>
          <br>
          <p data-aos="fade-up" data-aos-delay="200">
            우리 지역의 운동하기 좋은 장소는 어디인지, 운동 후 모임 회식은 어디로 갈지 고민이신가요?<br>
            Chat GPT의 추천을 받아보세요.
          </p>
          <RouterLink :to="{ name: 'chatgpt' }" class="btn custom-btn bg-color mt-3" data-aos="fade-up" data-aos-delay="300" data-toggle="modal"
            data-target="#membershipForm">Chat GPT 추천 받기</RouterLink>
        </div>

        <div class="col-lg-4 col-md-6 col-12" data-aos="fade-up" data-aos-delay="400">
          <div class="class-thumb">
            <img src="@/assets/images/class/yoga-class.jpg" class="img-fluid" alt="Class">
          </div>
        </div>

        <div class="mt-5 mt-lg-0 mt-md-0 col-lg-4 col-md-6 col-12" data-aos="fade-up" data-aos-delay="500">
          <div class="class-thumb">
            <img src="@/assets/images/class/crossfit-class.jpg" class="img-fluid" alt="Class">
          </div>
        </div>

        <div class="mt-5 mt-lg-0 col-lg-4 col-md-6 col-12" data-aos="fade-up" data-aos-delay="600">
          <div class="class-thumb">
            <img src="@/assets/images/class/cardio-class.jpg" class="img-fluid" alt="Class">
          </div>
        </div>

      </div>
    </div>
  </section>


  <!-- "새롭게" 영역 -->
  <section class="contact section mx-auto w-2/3" id="contact">
    <div class="container">
      <div class="row">

        <div class="col-lg-12 col-12 text-center mb-5">
          <h2 data-aos="fade-up" data-aos-delay="200" class="text-4xl font-bold">새롭게</h2>

          <h6 data-aos="fade-up" class="text-xl my-4">
            스포니티는 언제나 만족을 드리기 위해, 끊임 없이 새롭게 거듭나도록 노력하겠습니다.<br>
            서비스 이용에 불편한 점이 있으시다면 언제든 알려주세요.
          </h6>
        </div>

        <div class="ml-auto col-lg-5 col-md-6 col-12">
          <h2 class="mb-4 pb-2 text-4xl font-bold" data-aos="fade-up" data-aos-delay="200">이메일 불편접수</h2>

          <form action="#" method="post" class="contact-form webform" data-aos="fade-up" data-aos-delay="400"
            role="form">
            <input type="text" class="form-control" name="cf-name" placeholder="Name">

            <input type="email" class="form-control" name="cf-email" placeholder="Email">

            <textarea class="form-control" rows="14" name="cf-message" placeholder="Message"></textarea>

            <button type="submit" class="form-control" id="submit-button" name="submit">Send Email</button>
          </form>
        </div>

        <div class="mx-auto mt-4 mt-lg-0 mt-md-0 col-lg-5 col-md-6 col-12">
          <h2 class="mb-4 text-4xl font-bold" data-aos="fade-up" data-aos-delay="600">오시는 길</h2>

          <p data-aos="fade-up" data-aos-delay="800"><i class="fa fa-map-marker mr-1"></i> 대전광역시 유성구 동서대로
            98-39,<br>&nbsp;&nbsp;&nbsp;&nbsp;삼성화재 유성캠퍼스</p>
          <!-- How to change your own map point
	1. Go to Google Maps
	2. Click on your location point
	3. Click "Share" and choose "Embed map" tab
	4. Copy only URL and paste it within the src="" field below
-->
          <div class="google-map" data-aos="fade-up" data-aos-delay="900">
            <iframe
              src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d5404.041269432605!2d127.29837996556333!3d36.354428259656956!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x35654cad3d82caa9%3A0xb3fbce2dcaf4b67f!2z7IK87ISx7ZmU7J6sIOycoOyEseyXsOyImOybkA!5e0!3m2!1sko!2skr!4v1716364186264!5m2!1sko!2skr"
              width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy"
              referrerpolicy="no-referrer-when-downgrade"></iframe>
          </div>
        </div>

      </div>
    </div>
  </section>


  <!-- FOOTER -->
  <!-- <footer class="site-footer">
    <div class="container">
      <div class="row">

        <div class="ml-auto col-lg-4 col-md-5">
          <p class="copyright-text">Copyright &copy; 2020 Gymso Fitness Co.

            <br>Design: <a href="https://www.tooplate.com">Tooplate</a>
          </p>
        </div>

        <div class="d-flex justify-content-center mx-auto col-lg-5 col-md-7 col-12">
          <p class="mr-4">
            <i class="fa fa-envelope-o mr-1"></i>
            <a href="#">hello@company.co</a>
          </p>

          <p><i class="fa fa-phone mr-1"></i> 010-020-0840</p>
        </div>

      </div>
    </div>
  </footer> -->

</template>

<script setup>

</script>

<style scoped></style>