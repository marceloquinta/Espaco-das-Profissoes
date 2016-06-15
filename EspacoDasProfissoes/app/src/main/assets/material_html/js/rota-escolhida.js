jQuery(document).ready(function($) {
	var dadosRotas,
  		getRotas = $.ajax({
        url: 'json/rotas.json',
        type: 'GET'
      }),
      containerDias = $('div.itemslider'),
      card = $('div.card');

      card.children('span.titulo').text(getRotaId());


	getRotas.done(function(data){
      dadosRotas = data;
    });

    $.when(getRotas).then(function(){
      colocarDadosRotas();
    });


    function colocarDadosRotas(){
    	
    }
	
});