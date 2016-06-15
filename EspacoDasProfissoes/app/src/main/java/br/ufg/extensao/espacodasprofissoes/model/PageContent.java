package br.ufg.extensao.espacodasprofissoes.model;

public enum PageContent {
    CURSOS("lista-cursos.html"),
    CURSOS_PROGRAMACAO("curso-programacao.html"),
    CURSOS_INFO("curso-info.html"),
    PROGRAMA("programacao.html"),
    ROTAS("rotas.html"),
    ROTAS_CARD("rotas-card.html");

    private String pageName;

    private PageContent(String pageName){
        this.pageName = pageName;
    }

    public String getPageName(){
        return  pageName;
    }

}
