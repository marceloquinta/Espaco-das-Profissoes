jQuery(document).ready(function($) {
	var dadosRotas,
  		getRotas = $.ajax({
        url: 'json/rotas.json',
        type: 'GET'
      }),
      containerDias = $('div.itemslider > div.container'),
      card = $('div.card'),
      rotaEscolhida = 0; // variavel que armezena a rota escolhida

      // código para testar interface com Android
      // card.find('span.titulo').text(Android.getRotaId());


	getRotas.done(function(data){
      dadosRotas = data;
    });

    $.when(getRotas).then(function(){
      colocarDadosRotas();
    });


    function colocarDadosRotas(){
    	var cloneCard = card.clone(),
        infoDiaUm = dadosRotas.dias.d1[rotaEscolhida],
        infoDiaDois = dadosRotas.dias.d2[rotaEscolhida];
      containerDias.empty();
      $.each(infoDiaUm.espacos, function(index, val) {
         var cloneDoClone = cloneCard.clone();

         if (!val.transporte) {
          cloneDoClone.find('.requertransporte').addClass('esconder');
         }

         cloneDoClone.find('span.titulo').text(val.nome);

         if (val.extra) {
           cloneDoClone.find('span.subtitulo').text(val.extra);
         } else {
          cloneDoClone.find('span.subtitulo').addClass('esconder');
         }

         var cloneTagHorario = cloneDoClone.find('span.horario').clone();
         var conjHorarios = val.horario;
         cloneDoClone.find('span.horario').remove();
         $.each(conjHorarios, function(index2, val2) {
            var cloneHorario = cloneTagHorario.clone();
            cloneHorario.text(val2).appendTo(cloneDoClone.find('div.content').eq(1));
         });

         cloneDoClone.find('span.local').text(val.local);

         containerDias.eq(0).append(cloneDoClone);
      });

      $.each(infoDiaDois.espacos, function(index, val) {
         var cloneDoClone = cloneCard.clone();

         if (!val.transporte) {
          cloneDoClone.find('.requertransporte').addClass('esconder');
         }

         cloneDoClone.find('span.titulo').text(val.nome);

         if (val.extra) {
           cloneDoClone.find('span.subtitulo').text(val.extra);
         } else {
          cloneDoClone.find('span.subtitulo').addClass('esconder');
         }

         var cloneTagHorario = cloneDoClone.find('span.horario').clone();
         var conjHorarios = val.horario;
         cloneDoClone.find('span.horario').remove();
         $.each(conjHorarios, function(index2, val2) {
            var cloneHorario = cloneTagHorario.clone();
            cloneHorario.text(val2).appendTo(cloneDoClone.find('div.content').eq(1));
         });

         cloneDoClone.find('span.local').text(val.local);

         containerDias.eq(1).append(cloneDoClone);
      });
    }
	
});