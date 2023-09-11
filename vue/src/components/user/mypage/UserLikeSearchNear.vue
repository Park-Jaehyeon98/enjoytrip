<template>
  <div>
    <div id="left">
      
      <div id="select-area">
        <h4>원하는 숙박업소 유형을 선택하세요!</h4>
        <b-form-select placeholder="숙박업소 유형 선택" class="form-select" v-model="requests.keyword" :options="keywordOptions"></b-form-select>
        <!-- <b-form-select placeholder="정렬 기준" class="form-select" v-model="requests.rankby" :options="rankOptions"></b-form-select> -->
        <b-btn @click="searchPlaces" variant="outline-primary" style="width:100%; margin-top:10px">찾기</b-btn>
      </div>


      <div class="place-content">
        <div v-for="place in places" :key="place.place_id" class="place" v-b-toggle="place.place_id">
          <div class="place-content-title">
            <h3>{{ place.name }}</h3>
          </div>

          <div class="place-content-card">
            <b-collapse :id="place.place_id">
              <b-card>
                <div class="place-details">
                  <h5><b>{{ place.vicinity }}</b></h5>
                  <h5 v-if="place.business_status=='OPERATIONAL'">영업중</h5>
                  <h5 v-else-if="place.business_status=='CLOSED_TEMPORARILY'">영업 종료</h5>
                  <h5 v-else-if="place.business_status=='CLOSED_PERMANENTLY'">영업 중지</h5>
                  <b>{{ place.user_ratings_total }}</b> 명의 사용자들로부터 <b>{{ place.rating }}</b>점을 받은 업소입니다.
                </div>
              </b-card>
            </b-collapse>
          </div>
        </div>
      </div>
    </div>

    <div id="map-area">
      <div id="map" ref="map" style="height: 100%; width:100%"></div>
      
    </div>
    <b-alert id="da" v-model="showDismissibleAlert" variant="primary" dismissible><h1>내 맘에 드는 관광지 근처의 숙박업소를 찾아보세요!</h1></b-alert>
  </div>

</template>

<style scoped src="@/assets/css/user_like_search_near.css">
</style>

<script defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBwEdKqeZ9A1d12Cg07hxcMjZCQMOzFJk0&libraries=places&callback=initMap">
    </script>
<script>
// import { MarkerClusterer } from "@googlemaps/markerclusterer";

import apiInstance from "@/api/http";
const http = apiInstance();

export default {

  data() {
    return {
      showDismissibleAlert : true,  
      contentId : this.$route.params.contentId,
      attraction : {},
      center: {
        lat : 37.12345,
        lng : -122.6789 
      },
      requests : {
        radius : "3000",
        keyword : "호텔",
        rankby : "prominence",
        lanugague : "korean",
      },
      keywordOptions : ["호텔", "펜션", "모텔"],
      rankOptions : [
        { value: "prominence", text: "중요도 순" },
        { value: "distance", text: "가까운 순" }
      ],
      places : [],
      map : null
    }
  },
  created(){
    // console.log(this.contentId);
    http.get("/attraction/" + this.contentId).then(({ data }) => {
      // console.log("detail:", data);
      this.attraction = data;
      // console.log(this.attraction);
      this.center.lat = data.latitude;
      this.center.lng = data.longitude;
      // console.log(`created. lat:${this.center.lat}, lng : ${this.center.lng}`)
      this.initMap();
    });
  },
  methods: {
    searchPlaces(){
      // const proxyUrl = "https://cors-anywhere.herokuapp.com/";
      // let url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
      // let key = "AIzaSyBwEdKqeZ9A1d12Cg07hxcMjZCQMOzFJk0";
      // url += `location=${this.center.lat},${this.center.lng}`
      // url += `&keyword=${this.requests.keyword}`;
      // url += `&radius=${this.requests.radius}`;
      // url += `&language=korean`;
      // url += `&rankby=${this.requests.rankby}`
      // url += `&key=${key}`;

      // axios.get(proxyUrl + url)
      // .then(resp =>{
      //   this.places = resp.data.results;
      //   this.addLocationToGoogleMaps();
      // })
      // .catch(err => {
      //   console.log(err.message);
      // })
      var service = new google.maps.places.PlacesService(this.map);
      service.nearbySearch(
        {
          location : new google.maps.LatLng(this.center.lat, this.center.lng),
          keyword : this.requests.keyword,
          radius : this.requests.radius,
          language : "korean",
          rankby : this.requests.rankby
        },
        ((results, status)=>{
          if (status == google.maps.places.PlacesServiceStatus.OK) {
            // console.log("찾기 결과", results)
            this.places = results;
            if(this.places.length!=0)
              this.addLocationToGoogleMaps();
            else
              alert("찾을 수 있는 숙박업소가 없습니다. 다른 유형으로 검색해보세요.")
          }
        })
      )
    },
    // 찾은 장소들 맵에 부착
    addLocationToGoogleMaps(){
      
      this.map = new google.maps.Map(this.$refs['map'], {
        zoom: 10,
        center: new google.maps.LatLng(this.center.lat, this.center.lng),
        // mapTypeId: google.maps.MapTypeId.ROADMAP,
        disableDefaultUI: true, // ui 삭제
        gestureHandling: "cooperative", // 컨트롤 눌러야만 확대 축소 가능
      });
      const centerMarker = new google.maps.Marker({
        position: new google.maps.LatLng(this.center.lat, this.center.lng),
        map: this.map,
        // content: pinViewScaled.element,
        icon: {
          url: "http://maps.google.com/mapfiles/ms/icons/red-dot.png", // Replace with the URL of your custom marker image
          // url: "liked32.png", // Replace with the URL of your custom marker image
          scaledSize: new google.maps.Size(40, 40), // Adjust the size of the marker image
        },
        // content: centerMarker.element,
        // content: pinViewBackground.element, 
        // content: pinViewBorder.element, 
        // content: pinViewGlyph.element,
      });
      
      // 지도 경계 조정하기. 가장자리의 불필요한 영역을 없애고 마커들이 지도 가장자리에 위치하도록 함
      const bounds = new google.maps.LatLngBounds(); // 경계 객체
      bounds.extend(centerMarker.position)
      var infowindow = new google.maps.InfoWindow();
      
      // 찾은 장소의 위도 경도로 마커 찍기
      this.places.forEach((place) => {
        const lat = place.geometry.location[0];
        const lng = place.geometry.location[1];

        const location = place.geometry.location
        //console.log(`place 하나 주소 => ${lat}, ${lng}, ${location}`)
        let marker = new google.maps.Marker({
          position: location,
          map: this.map,
          label : place.name[0]
        });
        bounds.extend(marker.position); // 각 마커의 위치 정보를 경계 객체에 넘겨줌

        // 마커 클릭 시 실행할 이벤트
        google.maps.event.addListener(marker, "click", () => {
          infowindow.setContent(`<div class="ui header">${place.name}</div><p>${place.vicinity}</p>`);
          // 맵의 중심을 마커로 이동
          this.map.panTo(marker.position);
          // 정보창은 marker에 위치함
          infowindow.open({
            anchor: marker,
            map : this.map,
          });
        });

      });
      //지도 객체에 지도 경계 객체 넘겨주기
      this.map.fitBounds(bounds);
      // 마커 클러스터링
      // const markerCluster = new markerClusterer.MarkerClusterer({ map, markers });
    },
    initMap(){
      let mapCenter = new google.maps.LatLng(this.center.lat, this.center.lng);
      // console.log("mapCenter", mapCenter)
      this.map = new google.maps.Map(this.$refs.map, {
        center: mapCenter,
        zoom: 15,
        disableDefaultUI: true,
        gestureHandling: "cooperative",
      });

      // const pinViewScaled = new google.maps.marker.PinView({
      //   scale: 1.5,
      // });

      // const pinViewBackground = new google.maps.marker.PinView({
      //   background: "#184645"
      // });

      // const pinViewBorder = new google.maps.marker.PinView({
      //   borderColor: "#137333",
      // });

      // const pinViewGlyph = new google.maps.marker.PinView({
      //   glyphColor: "white",
      // });
      

      const centerMarker = new google.maps.Marker({
        position: mapCenter,
        map: this.map,
        // content: pinViewScaled.element,
        icon: {
          url: "http://maps.google.com/mapfiles/ms/icons/red-dot.png", // Replace with the URL of your custom marker image
          // url: "liked32.png", // Replace with the URL of your custom marker image
          scaledSize: new google.maps.Size(40, 40), // Adjust the size of the marker image
        },
        // content: centerMarker.element,
        // content: pinViewBackground.element, 
        // content: pinViewBorder.element, 
        // content: pinViewGlyph.element,
      });
    }
  },
};
</script>
