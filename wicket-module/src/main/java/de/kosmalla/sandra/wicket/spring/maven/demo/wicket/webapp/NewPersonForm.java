package de.kosmalla.sandra.wicket.spring.maven.demo.wicket.webapp;

import org.apache.wicket.model.CompoundPropertyModel;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.spring.injection.annot.SpringBean;
import de.kosmalla.sandra.wicket.spring.maven.demo.wicket.webapp.repository.PersonRepository;
import de.kosmalla.sandra.wicket.spring.maven.demo.wicket.webapp.domain.Person;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;


public class NewPersonForm extends Form<Person> {

    @SpringBean
    private PersonRepository personRepository;

    public NewPersonForm(String id){
        super(id, new CompoundPropertyModel<Person>(new Person()));
    }

    public NewPersonForm(String id, IModel<Person> model) {
        super(id, model);
        add(new TextField<String>("firstName"));
        add(new TextField<String>("lastName"));
        add(new Button("submitButton"));

    }

    /**  */
    private static final long serialVersionUID = -8916183430933475415L;

    @Override
    protected void onSubmit() {
        super.onSubmit();
        personRepository.save(getModelObject());
        setResponsePage(HomePage.class);

    }

}
