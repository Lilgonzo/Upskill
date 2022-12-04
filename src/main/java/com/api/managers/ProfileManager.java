package com.api.managers;

import com.api.entities.JWT;
import com.api.entities.Profile;
import com.api.utils.DbComm;
import com.api.utils.JWTUtil;
import jakarta.ws.rs.core.SecurityContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ProfileManager {

    public JWT createProfile(Profile profile) throws Exception {
        String sql = "insert into profile(username, password, email) values (?, ?, ?)";

        try (
                Connection connection = DbComm.getConnection();
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
            jwt.setJWT(JWTUtil.getJwts(String.valueOf(resultSet.getInt(1))));

            return jwt;
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    public Profile getProfile(SecurityContext securityContext, String username) throws Exception {
        // todo

        return null;
    }

    public void updateUsername(SecurityContext securityContext, Profile profile) throws Exception {
        String sql =
                "update profile " +
                        "set username=? where userID=" + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbComm.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, profile.getUsername());
            preparedStatement.executeUpdate();

        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void updatePassword(SecurityContext securityContext, Profile profile) throws Exception {
        String sql =
                "update profile " +
                        "set password=? where userID=" + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbComm.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, profile.getPassword());
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void updateBio(SecurityContext securityContext, Profile profile) throws Exception {
        String sql =
                "update profile set bio=? where userID=" + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbComm.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, profile.getBio());
            preparedStatement.executeUpdate();

        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void updateEmail(SecurityContext securityContext, Profile profile) throws Exception {
        String sql =
                "update profile set email=? where userID=" + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbComm.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
                ) {
            statement.setString(1, profile.getEmail());
            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void deleteProfile(SecurityContext securityContext) throws Exception {
        String sql =
                "delete from profile where userID=" + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbComm.getConnection();
                Statement statement = connection.createStatement()
                ) {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<Profile> getProfilesWithSimilarSkills(SecurityContext securityContext) throws Exception {
        String userId = securityContext.getUserPrincipal().getName();

        String sql =
                "select distinct bio, username, email from profile p " +
                    "join skill s on p.userID=s.userID " +
                    "join skilltype st on s.skillTypeID = st.skillTypeID " +
                "where " +
                    "p.userID!=" + userId + " and " +
                    "s.skillTypeID IN (select s1.skillTypeID from skill s1 where s1.userID=" + userId + ")";

        try (
                Connection connection = DbComm.getConnection();
                Statement statement = connection.createStatement()
                ) {

            ResultSet resultSet = statement.executeQuery(sql);

            Profile profile;
            List<Profile> profiles = new LinkedList<>();
            while (resultSet.next()) {
                profile = new Profile();
                profile.setBio(resultSet.getString("bio"));
                profile.setEmail(resultSet.getString("email"));
                profile.setUsername(resultSet.getString("username"));

                profiles.add(profile);
            }

            return profiles;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public JWT loginProfile(Profile profile) throws Exception {
        String sql = "select userID from profile where username=? and password=?";

        try (
                Connection connection = DbComm.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                ) {
            preparedStatement.setString(1, profile.getUsername());
            preparedStatement.setString(2, profile.getPassword());
            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();

            if (!resultSet.next())
                throw new Exception("User Not Found");

            // produce new jwts for user
            JWT jwt = new JWT();
            jwt.setJWT(JWTUtil.getJwts(String.valueOf(resultSet.getInt(1))));

            resultSet.close();

            return jwt;
        }
    }
    
     public List<Profile> getProfilesWithSimilarInterest(SecurityContext securityContext) throws Exception {
         
     }
    
}
