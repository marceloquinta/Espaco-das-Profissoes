jQuery(document).ready(function($) {
    var dadosCurso,
      getDadosCurso = $.ajax({
      url: 'json/cursos.json',
      type: 'GET',
    }),
      lista = $('div.lista ul');
      

    getDadosCurso.done(function(data){
      dadosCurso = data;
      popularCursos();
    });

    function popularCursos(){
      var itemListaTemplate = lista.find('li').eq(0).clone();
      lista.empty();
      console.log(itemListaTemplate);
      $.each(dadosCurso.regionais[0].cursos, function(index, val) {
         var itemclonado = itemListaTemplate.clone();
         itemclonado.children('span.titulo').text(val.nome);
         itemclonado.children('span.subtitulo').text('noturno');
         lista.append(itemclonado);
      });
         adicionarRipple();
      
    }


    
  });