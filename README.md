# enjoytrip

## 여행 관광지 주변 숙소 추천 웹 프로젝트

> 기간 : 2023. 05. 18 ~ 2023.05. 25
> 역할 : 팀장(2인 1팀)
> 언어 : `Java`, `Java Script`
> 환경 및 사용 기술 : `Eclipse` , `SpringBoot`, `Mybatis`, `Vue.js` , `Vuex` , `Html5` , `Css` , `BootStrap` , `Mysql` , `Kakao Map API,` `Google Map API` , `Places API`

- **여행 관광지 주변 숙수 추천 웹 프로젝트**
  - 프로젝트 개요
    - SSAFY에서 진행한 프론트, 백엔드 프로젝트로 이론과 실습으로 배웠던 개발 지식들을 실제 프로젝트에 적용
    - 해당 프로젝트는 사용자에게 관광지 목록을 보여주고, 좋아요 목록에 등록된 관광지 주변의 숙박 업소를 유형별로 보여주는 것이 목표
  - 백엔드
    - 백엔드 개발은 관광지 공공데이터를 SQL 파일로 제공받아 Mysql에 적용한 후, Spring Boot를 사용하여 프로젝트의 요구사항에 맞춰 API 개발
    - 모든 API는 페이지 대신 JSON 객체 형식으로 반환할 수 있도록 REST API로 구현
    - 요구사항은 관광지, 사용자, 게시판 부분으로 나누어져 있으며, 관광지와 게시판의 댓글, 관광지 종아요 기능에 대한 세부적인 구현
  - 프론트엔드
    - 프론트엔드 개발은 Vue.js를 활용해 백엔드에서 제공되는 API를 비동기식으로 호출하여 데이터를 출력할 수 있도록 구현
    - SPA 패러다임을 따라 개발, Views 폴더에는 요구사항에 따른 Vue 파일을 배치하고, 하위 컴포넌트는 Components 폴더에 생성하여 라우터를 통해 페이지 이동 효과를 구현
    - Kakao Map API를 사용하여 관광지의 세부 정보 확인 시 위치를 표시할 수 있도록 구현
    - 주변 숙박 업소 검색 기능을 구현하기 위해 Google에서 제공하는 Map API와 Places API를 활용
    - 사용자가 좋아요 목록에서 관광지를 선택하면 해당 관광지의 위도, 경도 정보를 주변 숙박 업소 표시 컴포넌트로 전달하여 위치를 표시하고 주변 장소를 검색하도록 구현
      ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1cc21856-2529-4a56-8bc8-40c103f6668a/Untitled.png)
      ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/59a67d9e-60d2-4cf8-9854-0f6356fcf98d/Untitled.png)
      ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/216aa7ee-7c3e-4b28-8619-7aac5736143e/Untitled.png)
      ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2a42b4c3-839e-4cb7-9180-7661801894d6/Untitled.png)
      ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7dc784f5-0baf-473d-a8d9-37be54a18c96/Untitled.png)
      ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/141b32ea-a3dd-411d-9cb3-a19a454b15fc/Untitled.png)
      ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/df7b3699-5289-4414-93d9-0d99c3ade18e/Untitled.png)
      ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/8d09dcd9-40af-43cf-83d9-7dab3f84f630/Untitled.png)
