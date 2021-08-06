package spring.ioc;


public class PersonServiceImpl implements PersonService{
    private PersonDao personDao;
    private Integer age;

    public PersonDao getPersonDao() {
        return personDao;
    }

    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public void savePerson() {
        System.out.println("age:"+age);
        System.out.println("service中的save方法调用成功");
        personDao.savePerson();
    }

}
