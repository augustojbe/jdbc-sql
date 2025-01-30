import dao.UserPosDao;
import model.BeanUserFone;
import model.Telefone;
import model.UserposJava;
import org.junit.Test;

import java.util.List;

public class TesteBancoJdbc {

    @Test
    public void initBanco(){
        UserPosDao userPosDao = new UserPosDao();
        UserposJava userposJava = new UserposJava();

        userposJava.setNome("Paulo");
        userposJava.setEmail("paulojbe@hotmail.com");

        userPosDao.salvar(userposJava);


    }

    @Test
    public void initListar(){
        UserPosDao dao = new UserPosDao();
        try {
            List<UserposJava> list = dao.list();

            for(UserposJava userposJava : list){
                System.out.println(userposJava.getNome() + " " + userposJava.getEmail());
                System.out.println("----------------------------------------------------------------");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void initBuscar(){

        UserPosDao dao = new UserPosDao();

        try {
            UserposJava userposJava = dao.buscar(5L);

            System.out.println(userposJava);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void initAtualizar(){

        UserPosDao dao = new UserPosDao();

        try{
            UserposJava objetoBanco = dao.buscar(5l);

            objetoBanco.setNome("Maria Carneiro");

            dao.atualizar(objetoBanco);

            System.out.println(objetoBanco);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void initDeletar(){

        try{
            UserPosDao dao = new UserPosDao();
            dao.delete(8l);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeInserteTelefone(){
        Telefone telefone = new Telefone();
        telefone.setNumero("(85)95890-8686");
        telefone.setTipo("trabalho");
        telefone.setUsuario(9L);

        UserPosDao dao = new UserPosDao();
        dao.salvarTelefone(telefone);

        System.out.println(telefone.getNumero() + " " + telefone.getTipo() + " Salvo com sucesso");


    }

    @Test
    public void TestCarregaFonesUser () {

        UserPosDao dao = new UserPosDao();

        List<BeanUserFone> beanUserFones = dao.listaUserFone(9L);

        for (BeanUserFone beanUserFone : beanUserFones){
            System.out.println(beanUserFone);
            System.out.println("------------------------------------------------------------------------------------");
        }

    }

    @Test
    public void testeDeleteFone () {

        UserPosDao dao = new UserPosDao();
        dao.deleteFonesPorUser(10L);

    }



}
