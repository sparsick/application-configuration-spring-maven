package de.kosmalla.sandra.sample.wicket.webapp;

import de.kosmalla.sandra.sample.wicket.webapp.domain.Person;
import de.kosmalla.sandra.sample.wicket.webapp.repository.PersonRepository;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private PersonRepository personRepository;


    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(new PersonListView("personList", personRepository.findAllPersons()));

        add(new NewPersonForm("newPersonForm", new CompoundPropertyModel<Person>(new Person())));

    }
}
