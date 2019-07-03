<template>
  <div id="dmap">
    <div id="map" style="width:100%; height:700px;"></div>
  </div>
</template>
<!--
다음본사 : 33.450701, 126.570667
다른곳 : 37.51207412593136, 127.05902969025047
//-->
<script>
  export default {
    name: 'dmap',
    data() {
      return {
        map: {}
      }
    },
    mounted() {
      var container = document.getElementById('map');
      var mapOptions = {
        center: new daum.maps.LatLng('33.450701', '126.570667'),
        level: 4
      };
      var map = new daum.maps.Map(container, mapOptions);
      this.$data.map = map
    },
    methods: {
      viewMap(xposition, yposition, message) {
        let moveLatLon = new kakao.maps.LatLng(yposition, xposition);
        this.$data.map.panTo(moveLatLon);
        let iwContent = '<div style="padding:5px;">' + message + '</div>';
        let iwPosition = new kakao.maps.LatLng(yposition, xposition);
        let iwRemoveable = true;
        let infowindow = new kakao.maps.InfoWindow({
          map: this.$data.map,
          position : iwPosition, 
          content : iwContent,
          removable : iwRemoveable
        });
      }
    }
  }
</script>

<style scoped>

</style>