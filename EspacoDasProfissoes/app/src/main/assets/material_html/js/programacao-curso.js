jQuery(document).ready(function($) {
    var dadosCurso,
      dadosRedundantes,
      dadosEspaco,
      dadosPalestras,
      getDadosCurso = $.ajax({
      url: 'json/cursos.json',
      type: 'GET',
    }),
      getRedundantes = $.ajax({
        url: 'json/dados-redundantes.json',
        type: 'GET',
      }),

      getEspacos = $.ajax({
        url: 'json/espacos_interativos.json',
        type: 'GET',
      }),

      getPalestras = $.ajax({
        url: 'json/palestras-cursos.json',
        type: 'GET',
      }),
      domInfo = $('div.card'),
      regionalSelecionada = localStorage.getItem('curso-regional'),
      cursoSelecionado = localStorage.getItem('curso-index');
      


    getDadosCurso.done(function(data){
      dadosCurso = data;
    });

    getRedundantes.done(function(data){
      dadosRedundantes = data;
    });

    getEspacos.done(function(data){
      dadosEspaco = data;
    });

    getPalestras.done(function(data){
      dadosPalestras = data;
    });

    $.when(getDadosCurso, getRedundantes, getEspacos, getPalestras).then(function(){
      colocarDados();
    });
    

    function colocarDados(){
      console.log('ajax carregou');
      var numeroPalestra = dadosCurso.regionais[regionalSelecionada].cursos[cursoSelecionado].pal_numero;
      var numeroEspaco = dadosCurso.regionais[regionalSelecionada].cursos[cursoSelecionado].ei_numero;
      var cardPalestra = domInfo.filter("[data-tipo='info-palestras']");
      var cardEspaco = domInfo.filter("[data-tipo='info-espaco']");
      var horariosPalestra = cardPalestra.find('span.horario');

      horariosPalestra.each(function(index, el) {
       $(el).text(
        dadosRedundantes
        .horarios_palestras_cursos[
          dadosPalestras
          .palestras[numeroPalestra]
          .horarios
        ][index])   
      });

      cardPalestra.find('span.local').text(
        dadosPalestras
        .palestras[
          numeroPalestra]
        .local)
    }


    
  });