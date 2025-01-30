package dao;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.UserposJava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserPosDao {

    private Connection connection;

    public UserPosDao(){
        connection = SingleConnection.getConnection();
    }

    public void salvar(UserposJava userposJava){

       try {
           String sql = "insert into userposjava(nome, email) values (?, ?)";
           PreparedStatement inserte = connection.prepareStatement(sql);
           inserte.setString(1, userposJava.getNome());
           inserte.setString(2, userposJava.getEmail());
           inserte.execute();
           connection.commit();

       } catch (Exception e){
           try {
               connection.rollback(); // reverte a operação
           } catch (SQLException ex) {
               throw new RuntimeException(ex);
           }
           e.printStackTrace();
       }

    }

    public void salvarTelefone(Telefone telefone){

        try{
            String sql =  "insert into telefoneuser (numero, tipo, usuariopessoa) values (?,?,?)";
            PreparedStatement inserte = connection.prepareStatement(sql);
            inserte.setString(1, telefone.getNumero());
            inserte.setString(2, telefone.getTipo());
            inserte.setLong(3, telefone.getUsuario());
            inserte.execute();
            connection.commit();

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }




    }

    public List<UserposJava> list () throws Exception {

        List<UserposJava> list = new ArrayList<UserposJava>();

        String sql = "select * from userposjava";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()){
            UserposJava userposJava = new UserposJava();
            userposJava.setId(resultado.getLong("id"));
            userposJava.setNome(resultado.getString("nome"));
            userposJava.setEmail(resultado.getString("email"));

            list.add(userposJava);
        }

        return list;
    }

    public UserposJava buscar (Long id) throws Exception {

        UserposJava retorno = new UserposJava();

        String sql = "select * from userposjava where id = " + id;

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()){ // retorna apela um ou nenhum

            retorno.setId(resultado.getLong("id"));
            retorno.setNome(resultado.getString("nome"));
            retorno.setEmail(resultado.getString("email"));


        }

        return retorno;
    }

    public List<BeanUserFone> listaUserFone (Long id){

        List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();

        String sql = " select nome, numero, email from telefoneuser as fone ";
        sql += " inner join userposjava as userpos ";
        sql += " on fone.usuariopessoa = userpos.id ";
        sql += " where userpos.id = " + id;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                BeanUserFone userFone = new BeanUserFone();
                userFone.setNome(resultSet.getString("nome"));
                userFone.setNumero(resultSet.getString("numero"));
                userFone.setEmail(resultSet.getString("email"));

                beanUserFones.add(userFone);

            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
        return  beanUserFones;



    }

    public  void atualizar(UserposJava userposJava){

       try{
           String sql = "update userposjava set nome = ? where id = " + userposJava.getId();

           PreparedStatement statement = connection.prepareStatement(sql);
           statement.setString(1, userposJava.getNome());

           statement.execute();
           connection.commit();
       } catch (Exception e){
           try {
               connection.rollback();
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
           e.printStackTrace();
       }

    }

    public void delete(Long id){
        try {

            String sql = "delete from userposjava where id = " + id;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            connection.commit();

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void deleteFonesPorUser(Long id){

        try {
            String sqlFone = "delete from telefoneuser where usuariopessoa =" + id;
            String sqlUser = "delete from userposjava where id =" + id;

            PreparedStatement statement = connection.prepareStatement(sqlFone);
            statement.executeUpdate();
            connection.commit();

            statement = connection.prepareStatement(sqlUser);
            statement.executeUpdate();
            connection.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }


    }
}
