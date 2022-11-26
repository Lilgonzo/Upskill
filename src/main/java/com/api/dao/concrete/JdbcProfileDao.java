package com.api.dao.concrete;

import com.api.entity.JWT;
import com.api.entity.Profile;
import com.api.dao.interfaces.ProfileDao;
import com.api.util.DbUtil;
import com.api.util.JWTUtil;
import jakarta.ws.rs.core.SecurityContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcProfileDao implements ProfileDao {

    @Override
    public JWT createProfile(Profile profile) throws Exception {
        String sql = "insert into profile(username, password, email) values (?, ?, ?)";

        try (
                Connection connection = DbUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                ) {

            preparedStatement.setString(1, profile.getUsername());
            preparedStatement.setString(2, profile.getPassword());
            preparedStatement.setString(3, profile.getEmail());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            // issue jwt
            JWT jwt = new JWT();
            jwt.setJWT(JWTUtil.getJwts(String.valueOf(resultSet.getInt(1)), false));

            return jwt;
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    @Override
    public Profile getProfile(SecurityContext securityContext, String username) throws Exception {
        // todo

        return null;
    }

    @Override
    public void updateUsername(SecurityContext securityContext, Profile profile) throws Exception {
        // todo username format invalid
            // todo throw invalid format exception

        String sql =
                "update profile " +
                        "set username=? where userID=" + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, profile.getUsername());
            preparedStatement.executeUpdate();

        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void updatePassword(SecurityContext securityContext, Profile profile) throws Exception {
        // todo password format invalid
            // todo throw invalid format exception

        String sql =
                "update profile " +
                        "set password=? where userID=" + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, profile.getPassword());
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void updateBio(SecurityContext securityContext, Profile profile) throws Exception {
        // todo bio format invalid
            // todo throw invalid format exception

        String sql =
                "update profile " +
                        "set bio=? where userID=" + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, profile.getBio());
            preparedStatement.executeUpdate();

        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void updateEmail(SecurityContext securityContext, Profile profile) throws Exception {
        // todo email format invalid
            // todo throw invalid format exception

    }

    @Override
    public void deleteProfile(SecurityContext securityContext) throws Exception {


    }

    @Override
    public JWT loginProfile(Profile profile) throws Exception {


        return null;
    }
}
