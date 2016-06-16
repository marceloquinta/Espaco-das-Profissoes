package br.ufg.extensao.espacodasprofissoes.web;

import android.content.Context;
import android.webkit.JavascriptInterface;

import org.greenrobot.eventbus.EventBus;

import br.ufg.extensao.espacodasprofissoes.model.Course;
import br.ufg.extensao.espacodasprofissoes.model.Route;

public class CourseWebInterface extends WebAppInterface{

    private Course course;

    /** Instantiate the interface and set the context */
    public CourseWebInterface(Context c) {
        super(c);
    }

    /**
     * Usado para recuperar o id do curso atual
     */
    @JavascriptInterface
    public int getCursoId() {
        return course.getId();
    }

    @JavascriptInterface
    public int getRegionalId() {
        return course.getRegionalId();
    }

    @JavascriptInterface
    public void setCurso(String nomeCurso, int idCurso, int idRegional) {
        if(course == null){
            course = new Course();
        }
        course.setId(idCurso);
        course.setRegionalId(idRegional);
        course.setName(nomeCurso);
        EventBus.getDefault().post(course);
    }

}
