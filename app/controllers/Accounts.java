package controllers;

import models.Member;
import models.Station;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class Accounts extends Controller
{
    public static void signup()
    {
        render("signup.html");
    }

    public static void index()
    {
        Member member = Accounts.getLoggedInMember();

        Logger.info("Rendering Account " + member.email);
        render ("account.html", member);
    }

    public static void login()
    {
        render("login.html");
    }

    public static void register(String firstname, String lastname, String email, String password)
    {
        Logger.info("Registering new user " + email);
        Member member = new Member(firstname, lastname, email, password);
        member.save();
        redirect("/");
    }

    public static void editMember(String firstname, String lastname, String email, String password)
    {
        Member member = getLoggedInMember();
        Logger.info("Registering edited user details " + email);
        member.firstname = firstname;
        member.lastname = lastname;
        member.email = email;
        member.password = password;
        member.save();
        redirect("/account");
    }

    public void deleteMember() {
        Member member = getLoggedInMember();

        Logger.info("Deleting account " + member.email);
        member.delete();
        Logger.info("Account " + member.email + " deleted");
        redirect("/");
    }

    public static void authenticate(String email, String password)
    {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        Member member = Member.findByEmail(email);
        if ((member != null) && (member.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect ("/dashboard");
        } else {
            Logger.info("Authentication failed");
            redirect("/login");
        }
    }

    public static void logout()
    {
        session.clear();
        redirect ("/");
    }

    public static Member getLoggedInMember()
    {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return member;
    }
}