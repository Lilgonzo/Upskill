package com.api.managers;

import com.api.entities.JWT;
import com.api.entities.Profile;
import com.api.entities.Skill;
import com.api.entities.SkillType;
import com.api.utils.DbComm;
import com.api.utils.JWTUtil;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.SecurityContext;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProfileManager {

    /**
     * Creates profile
     * @param profile the profile to create
     * @return jwt token
     * @throws Exception e
     */
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
        }
        catch (Exception e) {
            throw new Exception(e);
        }

    }

    /**
     * The profile to get
     * @param username the profile to retrieve
     * @return the profile
     * @throws Exception not found
     */
    public Profile getProfile(String username) throws Exception {
        String sql =
                "select * from profile where username=?";

        try (
                Connection connection = DbComm.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
                throw new NotFoundException("User not found");

            Profile profile = new Profile();
            profile.setEmail(resultSet.getString("email"));
            profile.setUsername(resultSet.getString("username"));
            profile.setBio(resultSet.getString("bio"));

            return profile;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Updates username
     * @param securityContext the profile to update
     * @param profile the username
     * @throws Exception e
     */
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


    public void updateRating(SecurityContext securityContext, Profile profile) throws Exception {
        String sql =
                "update rating " +
                        "set rating=?, set toUserID=?, set fromUserID=? where userID=" + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbComm.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, rating.getRating());
            preparedStatement.setString(2, rating.getToUser());
            preparedStatement.setString(3, rating.getFromUser());
            preparedStatement.executeUpdate();

        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Updates password
     * @param securityContext the user to update
     * @param profile the password
     * @throws Exception e
     */
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

    /**
     * Updates bio
     * @param securityContext the profile to update
     * @param profile the email
     * @throws Exception e
     */
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

    /**
     * Updates email
     * @param securityContext the profile to update
     * @param profile the email
     * @throws Exception e
     */
    public void updateEmail(SecurityContext securityContext, Profile profile) throws Exception {
        String sql =
                "update profile " +
                    "set email=? " +
                    "where userID = " + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbComm.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ) {
            preparedStatement.setString(1, profile.getEmail());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Deletes profile
     * @param securityContext the profile to delete
     * @throws Exception e
     */
    public void deleteProfile(SecurityContext securityContext) throws Exception {
        String sql =
                "delete from profile where userID=" + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbComm.getConnection();
                Statement statement = connection.createStatement()
                ) {
            statement.executeUpdate(sql);
            JWTUtil.invalidateJwts();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Retrieves profiles with similar skills
     * @param securityContext the user
     * @return a list of distinct profiles that contain similar skills
     * @throws Exception e
     */
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

    /**
     * Logins user given username and password.
     * @param profile containing username and profile
     * @return jwt token
     * @throws Exception - User not found
     */
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

    /**
     * Invalidates jwt token for logging out. Note: it is in here in-case we want to extend functionality to interact
     * with database and for testing purposes.
     */
    public void logout() {
        JWTUtil.invalidateJwts();
    }

    /**
     * Returns list of profiles with similar interests in skills.
     * @param securityContext the user
     * @return list of profiles with similar interests
     * @throws Exception e
     */
    public List<Profile> getProfilesWithSimilarInterest(SecurityContext securityContext) throws Exception {
       String userId = securityContext.getUserPrincipal().getName();
       String sql =
               "select distinct bio, username, email, skillType from profile p " +
                   "join interests s on p.userID=s.userID " +
                   "join skilltype st on s.skillTypeID = st.skillTypeID " +
               "where " +
                   "p.userID!=" + userId + " and " +
                   "s.skillTypeID IN (select s1.skillTypeID from interests s1 where s1.userID=" + userId + ")";
       
        try (
               Connection connection = DbComm.getConnection();
               Statement statement = connection.createStatement()
               ) {

            ResultSet resultSet = statement.executeQuery(sql);
            Profile profile;
            List<Profile> profiles = new LinkedList<>();
            SkillType skillType;
            Skill skill;
            List<Skill> skills = new LinkedList<>();
            while (resultSet.next()) {
                profile = new Profile();
                profile.setBio(resultSet.getString("bio"));
                profile.setEmail(resultSet.getString("email"));
                profile.setUsername(resultSet.getString("username"));

                skill = new Skill();
                skillType = new SkillType();
                skillType.setSkillType(resultSet.getString("skillType"));
                skill.setSkillType(skillType);

                skills.add(skill);

                profile.setSkills(skills);

                profiles.add(profile);
            }

            return profiles;

        } catch (Exception e) {
           throw new Exception(e);
        }
    }
    
}
