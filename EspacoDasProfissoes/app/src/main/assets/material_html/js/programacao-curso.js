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
      cardEspaco = $("div.card[data-tipo='info-espaco']").eq(0).clone();
      
      // código para testes no navegador
      var regionalSelecionada = localStorage.getItem('curso-regional');
      var cursoSelecionado = localStorage.getItem('curso-index');
      
      //código final para o app mesmo
      // var regionalSelecionada = Android.getRegionalId();
      // var cursoSelecionado = Android.getCursoId();

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
      // checar se ao menos existe programação desse curso
      if (dadosCurso.regionais[regionalSelecionada].cursos[cursoSelecionado].pal_numero != "") {

      // o número da palestra do curso escolhido
      var numeroPalestra = parseInt(
        dadosCurso
        .regionais[regionalSelecionada]
        .cursos[cursoSelecionado]
        .pal_numero
        );

      // o conjunto de espaços do curso escolhido
      var numerosEspaco = 
        dadosCurso
        .regionais[regionalSelecionada]
        .cursos[cursoSelecionado]
        .ei_numero;


      var horariosPalestra = cardPalestra.find('span.horario');
      horariosPalestra.each(function(index, el) {
        horarios = dadosPalestras.palestras[numeroPalestra].horarios;
       $(el).text(dadosRedundantes.horarios_palestras_cursos[horarios][index])   
      });

      cardPalestra.find('span.local').text(
        dadosPalestras
        .palestras[
          numeroPalestra]
        .local);

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
      
      $("[data-tipo='sem-info']").removeClass('esconder');
    }
    }


    
  });