jQuery(document).ready(function($) {
	var btRotas = $('div.lista.rotas ul li'),
		dadosRotas,
		getRotas = $.ajax({
      url: 'json/rotas.json',
      type: 'GET'
      });

	adicionarRipple();
	getRotas.done(function(data){
      dadosRotas = data;
    });

    $.when(getRotas).then(function(){
      criarBtsRotas();
    });


    function criarBtsRotas(){
    	btRotas.on('click', function(event) {
    		var indexClicado = btRotas.index($(this));

    		Android.setRota(indexClicado, 
    			dadosRotas
    			.dias
    			.d1[indexClicado]
    			.rota);
    		/* Act on the event */
    	});
    }
	
});