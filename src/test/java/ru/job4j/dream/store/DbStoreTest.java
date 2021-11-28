package ru.job4j.dream.store;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DbStoreTest {
    private static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = DbStoreTest.class.
                getClassLoader().getResourceAsStream("db.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(
                    config.getProperty("jdbc.url")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement1 = connection.prepareStatement("delete from candidate");
                PreparedStatement statement2 = connection.prepareStatement("delete from post");
             PreparedStatement statement3 = connection.
                     prepareStatement("ALTER TABLE candidate ALTER COLUMN id RESTART WITH 1");
             PreparedStatement statement4 = connection.
                     prepareStatement("ALTER TABLE post ALTER COLUMN id RESTART WITH 1")) {
            statement1.execute();
            statement2.execute();
            statement3.execute();
            statement4.execute();
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @Test
    public void whenCreatePost() {
        Store store = DbStore.instOf();
        Post post = new Post(0, "Java Job");
        store.savePost(post);
        Post postInDb = store.findPostById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenCreateCandidate() {
        Store store = DbStore.instOf();
        Candidate candidate = new Candidate(0, "Java Junior");
        store.saveCandidate(candidate);
        Candidate candidateInDb = store.findCandidateById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdatePost() {
        Store store = DbStore.instOf();
        Post manager = new Post(0, "Manager");
        Post programmer = new Post(0, "Programmer");
        Post devOps = new Post(1, "DevOps");
        store.savePost(manager);
        store.savePost(programmer);
        store.savePost(devOps);
        Post postInDb = store.findPostById(manager.getId());
        assertThat(postInDb.getName(), is(devOps.getName()));
    }

    @Test
    public void whenUpdateCandidate() {
        Store store = DbStore.instOf();
        Candidate one = new Candidate(0, "Tony");
        Candidate two = new Candidate(0, "Mike");
        Candidate three = new Candidate(1, "Bob");
        store.saveCandidate(one);
        store.saveCandidate(two);
        store.saveCandidate(three);
        Candidate candidateInDb = store.findCandidateById(one.getId());
        assertThat(candidateInDb.getName(), is(three.getName()));
    }

    @Test
    public void whenFindAllPosts() {
        Store store = DbStore.instOf();
        Post post1 = new Post(0, "Manager");
        Post post2 = new Post(0, "Programmer");
        store.savePost(post1);
        store.savePost(post2);
        List<Post> posts = List.of(new Post(1, "Manager"),
                new Post(2, "Programmer"));
        assertThat(store.findAllPosts(), is(posts));
    }

    @Test
    public void whenFindAllCandidates() {
        Store store = DbStore.instOf();
        Candidate candidate1 = new Candidate(0, "Tony");
        Candidate candidate2 = new Candidate(0, "Mike");
        store.saveCandidate(candidate1);
        store.saveCandidate(candidate2);
        List<Candidate> candidates = List.of(new Candidate(1, "Tony"),
                new Candidate(2, "Mike"));
        assertThat(store.findAllCandidates(), is(candidates));
    }
}
