<script setup>
import { reactive, ref } from 'vue';
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';

const props = defineProps({
  title: { Type: String, required: true },
  address: { Type: String, required: true },
});

// 서울 시청 좌표
const coordinate = reactive({
  lat: 37.566826,
  lng: 126.9786567,
});

const map = ref();

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef; // 지도 객체
  const geocoder = new kakao.maps.services.Geocoder();

  // 주소로 좌표 검색
  geocoder.addressSearch(props.address, (result, status) => {
    // 정상적으로 검색이 완료됐으면
    if (status === kakao.maps.services.Status.OK) {
      coordinate.lat = result[0].y;
      coordinate.lng = result[0].x;
    }
  });
};

const visibleRef = ref(false); // 초기에 정보창 숨김

const onClickKakaoMapMarker = () => {
  visibleRef.value = !visibleRef.value; // 정보창 보이기 토글
};
</script>

<template>
  <div><i class="fa-solid fa-map-location-dot"></i> 주소: {{ address }}</div>
  <kakao-map
    :lat="coordinate.lat"
    :lng="coordinate.lng"
    :level="3"
    :draggable="true"
    style="width: 100%"
    @onLoadKakaoMap="onLoadKakaoMap"
  >
    <kakao-map-marker
      :lat="coordinate.lat"
      :lng="coordinate.lng"
      :infoWindow="{ content: title, visible: visibleRef }"
      :clickable="true"
      @onClickKakaoMapMarker="onClickKakaoMapMarker"
    />
  </kakao-map>
</template>
