jQuery(document).ready(function($) {
    var dadosCurso,
      dadosRedundantes,
      dadosEspaco,
      dadosPalestras,
      getDadosCurso = $.ajax({
      url: 'json/cursos.json',
      type: 'GET',
      dataType: 'text'
    }),
      getRedundantes = $.ajax({
        url: 'json/dados-redundantes.json',
        type: 'GET',
        dataType: 'text'
      }),

      getEspacos = $.ajax({
        url: 'json/espacos_interativos.json',
        type: 'GET',
        dataType: 'text'
      }),

      getPalestras = $.ajax({
        url: 'json/palestras-cursos.json',
        type: 'GET',
        dataType: 'text'
      }),
      domContainer = $('div.container'),
      cardPalestra = $("div.card[data-tipo='info-palestras']").eq(0).clone(),
      cardEspaco = $("div.card[data-tipo='info-espaco']").eq(0).clone(),
      cardRegionais = $('div.card[data-tipo="sem-info"]'),
      infoSemprogama = $('div[data-tipo="sem-info-goiania"]');
      
      // código para testes no navegador
      // var regionalSelecionada = localStorage.getItem('curso-regional');
      // var cursoSelecionado = localStorage.getItem('curso-index');
      
      //código final para o app mesmo
      var regionalSelecionada = Android.getRegionalId();
      var cursoSelecionado = Android.getCursoId();

      $("div.card[data-tipo='info-palestras']").remove();
      $("div.card[data-tipo='info-espaco']").remove();
      


    getDadosCurso.done(function(data){
      dadosCurso = $.parseJSON(data);
    });

    getRedundantes.done(function(data){
      dadosRedundantes = $.parseJSON(data);
    });

    getEspacos.done(function(data){
      dadosEspaco = $.parseJSON(data);
    });

    getPalestras.done(function(data){
      dadosPalestras = $.parseJSON(data);
    });

    $.when(getDadosCurso, getRedundantes, getEspacos, getPalestras).then(function(){
      colocarDados();
    });
    

    function colocarDados(){
      // Checar se a regional é outra além de Goiânia
      if (regionalSelecionada != 0) {
        var regionalNome = dadosCurso.regionais[regionalSelecionada].nome;
        var dataEventoRegional = dadosCurso.regionais[regionalSelecionada].data_evento;


        cardRegionais.find('span[data-tipo="info-regional"]').text(regionalNome);
        cardRegionais.find('span[data-tipo="info-regional-data"]').text(dataEventoRegional);
        cardRegionais.removeClass('esconder');
      }else{
          var valorPalestra = dadosCurso.regionais[regionalSelecionada].cursos[cursoSelecionado].pal_numero;

          // checar se o curso tem programação no evento
          if (valorPalestra != "") {

          // o número da palestra do curso escolhido
          var numeroPalestra = parseInt(valorPalestra);

          // o conjunto de espaços do curso escolhido
          var numerosEspaco = 
            dadosCurso
            .regionais[regionalSelecionada]
            .cursos[cursoSelecionado]
            .ei_numero;


          var horariosPalestra = cardPalestra.find('span.horario');
          horariosPalestra.each(function(index, el) {
            horarios = dadosPalestras.palestras[numeroPalestra].horarios;
            var text = dadosRedundantes.horarios_palestras_cursos[horarios][index];
            
            if(!text) $(el).remove();
            else $(el).text(text);
          });

          var cloneDadosEspaco = cardEspaco.find('div.content').eq(0).clone();
          cardEspaco.find('div.content').remove();

          $.each(numerosEspaco, function(index, val) {
             clone2DadosEspaco = cloneDadosEspaco.clone();
             var nomeCursoEspaco = 
               dadosEspaco
               .espacos_interativos[val]
               .curso;
             var localEspaco = 
               dadosRedundantes
               .locais_espacos_interativos[ 
                 parseInt(
                  dadosEspaco
                  .espacos_interativos[val]
                  .local) ]
                 .nome+(
                  (dadosEspaco.espacos_interativos[val].sala != '') 
                  ? ', sala '+dadosEspaco.espacos_interativos[val].sala 
                  : ''
                  );
             clone2DadosEspaco.find('span.eventocurso').text(nomeCursoEspaco);
             clone2DadosEspaco.find('span.local').text(localEspaco);
             clone2DadosEspaco.appendTo(cardEspaco);
          });


          domContainer.append(cardPalestra);
          domContainer.append(cardEspaco);

          // Se realmente não há programação, colocar mensagem
        } else{ 
          
          infoSemprogama.removeClass('esconder');
        }
      }

      // checar se ao menos existe programação desse curso

      
    }


    
  });