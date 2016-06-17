jQuery(document).ready(function($) {
	var dadosProg,
      dadosRedundantes,
  		getProg = $.ajax({
        url: 'json/programacao.json',
        type: 'GET',
        dataType: "text"
      }),
       getRedundantes = $.ajax({
        url: 'json/dados-redundantes.json',
        type: 'GET',
        dataType: 'text'
      }),
      containerDias = $('div.itemslider > div.container'),
      cardGeral = $('div.card[data-tipo="palestrageral"]').clone(),
      cardPCursos = $('div.card[data-tipo="palestracursos"]').clone(),
      cardCultural = $('div.card[data-tipo="roteirocultural"]').clone();
      

  $('div.card').remove();
	getProg.done(function(data){
      dadosProg = $.parseJSON(data);
    });

  getRedundantes.done(function(data){
      dadosRedundantes = $.parseJSON(data);
    });

    $.when(getProg, getRedundantes).then(function(){
      colocarProgramacao();
    });


    function colocarProgramacao(){
      var eventos1dia = dadosProg.dias.d1;
      var eventos2dia = dadosProg.dias.d2;
      var tiposEvento = dadosRedundantes.tipos_de_evento;

      popularEInserirCards(eventos1dia, 0);
      popularEInserirCards(eventos2dia, 1);
      function popularEInserirCards(dia, containerIndex){
          $.each(dia, function(index, val) {
            switch(val.tipo){


              // Se for um card de palestra geral:
              case 0:
                var cloneGeral = cardGeral.clone();
                cloneGeral.find('label.palestrageral').text(tiposEvento[val.tipo]);

                cloneGeral.find('label.horario').text(val.horario);

                cloneGeral.find('span.titulo').text(val.Nome);

                if (val.extra != "") {
                  cloneGeral.find('span.subtitulo.extra').eq(0).text(val.extra);
                } else {
                 cloneGeral.find('span.subtitulo.extra').eq(0).addClass('esconder');
                }

                if (val.duracao != "") {
                  cloneGeral.find('span.horario').eq(0).text(val.duracao);
                } else {
                 cloneGeral.find('span.horario').eq(0).addClass('esconder');
                }

                cloneGeral.find('span.local').eq(0).text(val.local);
                containerDias.eq(containerIndex).append(cloneGeral);
                break;


                // Se for um card de palestras dos cursos:
              case 1:
                var clonePCursos = cardPCursos.clone();
                clonePCursos.find('label.palestracurso').text(tiposEvento[val.tipo]);

                clonePCursos.find('label.horario').text(val.horario);

                var cloneTagPCursos = clonePCursos.find('div.content').find('span.subtitulo.ncurso').eq(0).clone();
        
                var arrayPCursos = val.Nome;
                clonePCursos.find('div.content').find('span.subtitulo.ncurso').remove();
                $.each(arrayPCursos, function(index2, val2) {
                   var clone2PCursos = cloneTagPCursos.clone();
                   clone2PCursos.text(val2).appendTo(clonePCursos.find('div.content').eq(0));
                });

                
                containerDias.eq(containerIndex).append(clonePCursos);
                break;


                // Se for um card de evento cultural:

              case 2:
                var cloneCultural = cardCultural.clone();
                cloneCultural.find('label.roteirocultural').text(tiposEvento[val.tipo]);

                cloneCultural.find('label.horario').text(val.horario);

                cloneCultural.find('span.titulo').text(val.Nome);

                if (val.extra != "") {
                  cloneCultural.find('span.subtitulo.extra').eq(0).text(val.extra);
                } else {
                 cloneCultural.find('span.subtitulo.extra').eq(0).addClass('esconder');
                }

                if (val.duracao != "") {
                  cloneCultural.find('span.horario').eq(0).text(val.duracao);
                } else {
                 cloneCultural.find('span.horario').eq(0).addClass('esconder');
                }

                cloneCultural.find('span.local').eq(0).text(val.local);
                containerDias.eq(containerIndex).append(cloneCultural);
                break;
            }

        });
      }


    }
	
});