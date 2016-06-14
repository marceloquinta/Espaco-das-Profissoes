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
      lista = $('div.lista ul');
      


    getDadosCurso.done(function(data){
      dadosCurso = data;
    });

    getRedundantes.done(function(data){
      dadosRedundantes = data;
    });

    $.when(getDadosCurso, getRedundantes).then(function(){
      popularCursos();
    });
    

    function popularCursos(){
      var itemListaTemplate = lista.find('li').eq(0).clone();
      lista.empty();
      console.log(itemListaTemplate);
      $.each(dadosCurso.regionais[regional].cursos, function(index, val) {
         var itemclonado = itemListaTemplate.clone();
         itemclonado.children('span.titulo').text(val.nome);
         itemclonado.children('span.subtitulo').text(dadosRedundantes.cursos.graus[val.grau]+', '+dadosRedundantes.cursos.turnos[val.turno]);
         lista.append(itemclonado);
      });
         adicionarRipple();
         lista.children('li').on('click', function(event) {

          // Apenas para teste
          localStorage.setItem("curso-regional", regional);
          localStorage.setItem("curso-index", lista.children('li').index($(this)));
          window.location.href = "../curso-info.html";


           // método definido pelo Marcelo. arg1: regional; arg2: index do curso

           // metodoDoMarcelo(regional, lista.children('li').index($(this)));
         });
      
    }


    
  });