jQuery(document).ready(function($) {
    var dadosCurso,
      dadosRedundantes,
      getDadosCurso = $.ajax({
        url: 'json/cursos.json',
        type: 'GET',
        dataType: "text"
      }),
      getRedundantes = $.ajax({
        url: 'json/dados-redundantes.json',
        type: 'GET',
        dataType: "text"
      }),
      lista = $('div.lista > ul'),
      itemLista = lista.children('li').eq(0).clone();

    lista.empty();

    getDadosCurso.done(function(data){
      dadosCurso = $.parseJSON(data);
    });

    getRedundantes.done(function(data){
      dadosRedundantes = $.parseJSON(data);
    });

    $.when(getDadosCurso, getRedundantes).then(function(){
      popularCursos();
    });
    

    function popularCursos(){
      lista.each(function(index, el) {
        var listaAtual = $(el);
        
        $.each(dadosCurso.regionais[index].cursos, function(ind, valor) {
           var itemListaClone = itemLista.clone();
           itemListaClone.children('span.titulo').text(valor.nome);
           itemListaClone.children('span.subtitulo').text(dadosRedundantes.cursos.graus[valor.grau]+', '+dadosRedundantes.cursos.turnos[valor.turno]);
           listaAtual.append(itemListaClone);
        });
      });
      
         adicionarRipple();
         lista.children('li').on('click', function(event) {
          var regionalIndex = lista.index($(this).parent());
          var cursoIndex = $(this).parent().children('li').index($(this));
          console.log(dadosCurso.regionais[regionalIndex].cursos[cursoIndex].nome+' da região '+dadosCurso.regionais[regionalIndex].nome)
          

          // código para teste de escolha de curso
          // localStorage.setItem("curso-regional", regionalIndex);
          // localStorage.setItem("curso-index", cursoIndex);
          // window.location.href = "../curso-info.html";

          //código para o app de fato

          var nomeCursoEscolhido = dadosCurso.regionais[regionalIndex].cursos[cursoIndex].nome;
          Android.setCurso(
            nomeCursoEscolhido,
            cursoIndex,
            regionalIndex);
         });
      
    }


    
  });