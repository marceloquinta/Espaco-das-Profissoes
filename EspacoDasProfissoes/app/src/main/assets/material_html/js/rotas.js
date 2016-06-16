jQuery(document).ready(function($) {
	var btRotas = $('div.lista.rotas ul li'),
		dadosRotas,
		getRotas = $.ajax({
	      url: 'json/rotas.json',
	      type: 'GET',
	      dataType: "text"
	      
	  	});

	adicionarRipple();
	getRotas.done(function(data){
      dadosRotas = $.parseJSON(data);
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
    		var nomeRota = dadosRotas.d1[indexClicado].rota;
    		// console.log(indexClicado, nomeRota);

		    //chamando função
    		enviarRota(indexClicado, nomeRota);
    	});
    }

    
	
});