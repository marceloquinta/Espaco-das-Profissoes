jQuery(document).ready(function($) {
	var btRotas = $('div.lista.rotas ul li'),
		dadosRotas,
		getRotas = $.ajax({
	      url: 'json/rotas.json',
	      type: 'GET',
	      crossDomain: true
	  	});

	adicionarRipple();
	getRotas.done(function(data){
      dadosRotas = data;
    });

    $.when(getRotas).then(function(){
      criarBtsRotas();
    });

    //função que envia Rota escolhida p/ Android
    function enviarRota(indice, rota){
    	Android.setRota(indice, rota);
    }

    function criarBtsRotas(){
    	btRotas.on('click', function(event) {
    		var indexClicado = btRotas.index($(this));
    		var nomeRota = dadosRotas.dias.d1[indexClicado].rota;
    		console.log(indexClicado, nomeRota);

		    //chamando função
    		enviarRota(indexClicado, nomeRota);

    		
    	});
    }

    
	
});