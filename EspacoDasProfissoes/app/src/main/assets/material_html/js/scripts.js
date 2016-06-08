$(document).ready(function() {
  $('.button-collapse').sideNav({
      edge: 'right',
      closeOnClick: true
    }
  );

  $('.sliderabas').bxSlider({
    pagerCustom: '.tabs',
    touchEnabled: false,
    controls: false,
    adaptiveHeight: true
  });  
});