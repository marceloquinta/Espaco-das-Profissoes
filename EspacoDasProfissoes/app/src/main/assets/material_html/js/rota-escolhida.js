jQuery(document).ready(function($) {
	var dadosRotas,
  		getRotas = $.ajax({
        url: 'json/rotas.json',
        type: 'GET',
        dataType: "text"
      }),
      containerDias = $('div.itemslider > div.container'),
      card = $('div.card'),
      rotaEscolhida = Android.getRotaId(); // valor real para o app
      // rotaEscolhida = 3; //valor fixo para testes


	getRotas.done(function(data){
      dadosRotas = $.parseJSON(data);
    });

    $.when(getRotas).then(function(){
      colocarDadosRotas();
    });


    function colocarDadosRotas(){
    	var cloneCard = card.clone(),
        infoDiaUm = dadosRotas.d1[rotaEscolhida],
        infoDiaDois = dadosRotas.d2[rotaEscolhida];
      containerDias.empty();

      function popularEInserirCards(dia, containerIndex){
          $.each(dia.espacos, function(index, val) {
           var cloneDoClone = cloneCard.clone();

           if (!val.transporte) {
            cloneDoClone.find('.requertransporte').addClass('esconder');
           }

           cloneDoClone.find('span.titulo').text(val.nome);

           if (val.extra) {
             cloneDoClone.find('span.subtitulo').eq(0).text(val.extra);
           } else {
            cloneDoClone.find('span.subtitulo').eq(0).addClass('esconder');
           }

           var cloneTagHorario = cloneDoClone.find('span.horario').clone();
           var conjHorarios = val.horario;
           cloneDoClone.find('span.horario').remove();
           $.each(conjHorarios, function(index2, val2) {
              var cloneHorario = cloneTagHorario.clone();
              cloneHorario.text(val2).appendTo(cloneDoClone.find('div.content').eq(1));
           });

           if (val.local) {
             cloneDoClone.find('span.local').text(val.local);
           } else {
            cloneDoClone.find('span.local').addClass('esconder');
           }
           

           containerDias.eq(containerIndex).append(cloneDoClone);
        });
      }

      popularEInserirCards(infoDiaUm, 0);
      popularEInserirCards(infoDiaDois, 1);

    }
	
});