jQuery(document).ready(function($) {
    var dadosCurso,
      dadosRedundantes,
      getDadosCurso = $.ajax({
      url: 'json/cursos.json',
      type: 'GET',
    }),
      getRedundantes = $.ajax({
        url: 'json/dados-redundantes.json',
        type: 'GET',
      }),
      domInfo = $('div.lista ul li'),
      regionalSelecionada = localStorage.getItem('curso-regional'),
      cursoSelecionado = localStorage.getItem('curso-index');
      


    getDadosCurso.done(function(data){
      dadosCurso = data;
    });

    getRedundantes.done(function(data){
      dadosRedundantes = data;
    });

    $.when(getDadosCurso, getRedundantes).then(function(){
      colocarDados();
    });
    

    function colocarDados(){

      var dadosDoCursoSelecionado = dadosCurso.regionais[regionalSelecionada].cursos[cursoSelecionado];
      
      domInfo.eq(0).children('span.subtitulo').text(
          dadosRedundantes
          .cursos
          .graus
          [dadosDoCursoSelecionado
          .grau]
        );
      domInfo.eq(1).children('span.subtitulo').text(
          dadosRedundantes
          .cursos
          .processos_seletivos
          [dadosDoCursoSelecionado
          .ps]
        ); 
      domInfo.eq(2).children('span.subtitulo').text(
          dadosDoCursoSelecionado
          .duracao
          .toString()+' semestres'
        ); 
      domInfo.eq(3).children('span.subtitulo').text(
          dadosRedundantes
          .cursos
          .turnos
          [dadosDoCursoSelecionado
          .turno]
        ); 
      domInfo.eq(4).children('span.subtitulo').text(
          dadosDoCursoSelecionado
          .n_vagas
          .toString()
        );
      if (regionalSelecionada == 0) {
        domInfo.eq(5).removeClass('esconder').children('span.subtitulo').text(
          dadosRedundantes
          .cursos
          .campi
          [dadosDoCursoSelecionado
          .campus]
        ); 
      }
         
    }


    
  });