$(function() {

  var w = $(window).width(),
    h = $(window).height();
  //$('section').width(w);
  $('section').height(h);
  $('menu .container').height(h - 60);
  $('body').prepend('<div id="overlay">');

  $('#menu').click(function() {$('html').addClass('active');});
  $('#close-menu').click(function() {$('html').removeClass('active');});
  $('#overlay').click(function() {$('html').removeClass('active');});
  $(window).resize(function() {
    var w = $(window).width(),
      h = $(window).height();
    //$('section').width(w);
    $('section').height(h);
    $('menu .container').height(h - 60);
  });

});


function displayImage(imageTagId){
	var $body = $('body');
	var $originalImage = document.getElementById(imageTagId);
	var $blackout = $("<div id='blackout'>").css("display", "none");
	
	var $imageSrc = $("#"+imageTagId).attr("src");
	var $image = "<img src="+$imageSrc+" />";
    $blackout.append($image);
    $image = $blackout.find("img");
    // Ce block ne s'execute pas maintenant, mais au prochain click sur notre "blackout". Il se lit comme suit :
    // Au clic sur le fond...
    $blackout.click(function(){
      // On fait disparaitre progressivement la lightbox...
      $blackout.fadeOut(function(){
        // Puis on la supprime.
        $blackout.remove();
      })
    });
    
    // On ajoute notre lightbox au body.
    $body.append($blackout);
    //Et enfin nous la faisons apparaitre progressivement.
    $blackout.fadeIn();
    
    // Ces trois petites lignes permettent de centrer l'image en hauteur
    if($image.height() < $blackout.height()){
      $image.css("marginTop", ($blackout.height() - $image.height()) / 2);
    }
	
}

function gotoPhotos(albumId){
	window.location.href = "index_photos.xhtml?albumId="+albumId;
}


