package com.devdock.config;

import com.devdock.entity.*;
import com.devdock.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ResourceRepository resourceRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           ProjectRepository projectRepository,
                           TaskRepository taskRepository,
                           ResourceRepository resourceRepository,
                           TagRepository tagRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.resourceRepository = resourceRepository;
        this.tagRepository = tagRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            logger.info("Database already seeded. Skipping initial data creation.");
            return;
        }

        logger.info("Seeding realistic DevDock demo data...");

        // 1. Create Users
        User demoUser = new User(
            "Alex Developer",
            "demo@devdock.dev",
            passwordEncoder.encode("password123"),
            Role.ROLE_USER
        );
        demoUser.setBio("Full-stack software engineer & open source enthusiast building developer tools.");
        demoUser.setAvatarUrl("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200&auto=format&fit=crop&q=80");
        demoUser = userRepository.save(demoUser);

        User adminUser = new User(
            "DevDock Admin",
            "admin@devdock.dev",
            passwordEncoder.encode("admin123"),
            Role.ROLE_ADMIN
        );
        adminUser.setBio("System Administrator for DevDock.");
        userRepository.save(adminUser);

        // 2. Create Tags
        Tag tagReact = tagRepository.save(new Tag("react", "#38bdf8"));
        Tag tagSpring = tagRepository.save(new Tag("spring-boot", "#4ade80"));
        Tag tagJava = tagRepository.save(new Tag("java", "#fb923c"));
        Tag tagSysDesign = tagRepository.save(new Tag("system-design", "#a855f7"));
        Tag tagDatabase = tagRepository.save(new Tag("postgres", "#60a5fa"));
        Tag tagSecurity = tagRepository.save(new Tag("security", "#f43f5e"));

        // 3. Create Projects for Demo User
        Project p1 = new Project(
            demoUser,
            "DevDock Full-Stack Dashboard",
            "Centralized productivity and bookmark aggregator for engineers and CS students.",
            ProjectStatus.IN_PROGRESS,
            "https://github.com/alex-dev/devdock",
            "https://devdock.app"
        );
        p1 = projectRepository.save(p1);

        Project p2 = new Project(
            demoUser,
            "Algorithmic Trading Engine",
            "High-throughput event-driven order matching engine and backtesting framework in Java/Rust.",
            ProjectStatus.PLANNING,
            "https://github.com/alex-dev/algo-engine",
            ""
        );
        p2 = projectRepository.save(p2);

        Project p3 = new Project(
            demoUser,
            "Distributed Key-Value Store",
            "Raft consensus implementation with persistent LSM tree storage engine.",
            ProjectStatus.COMPLETED,
            "https://github.com/alex-dev/distributed-kv",
            "https://kv.alexdev.internal"
        );
        p3 = projectRepository.save(p3);

        // 4. Create Tasks
        Task t1 = taskRepository.save(new Task(
            p1, "Design PostgreSQL relational schema & indexes",
            "Create normalized tables for users, projects, tasks, resources, and tags.",
            TaskStatus.DONE, Priority.HIGH, LocalDate.now().minusDays(3)
        ));

        Task t2 = taskRepository.save(new Task(
            p1, "Implement Spring Security 6 & JWT Auth Filter",
            "Stateless JWT authentication filter chain with custom exception entrypoint.",
            TaskStatus.DONE, Priority.URGENT, LocalDate.now().minusDays(2)
        ));

        Task t3 = taskRepository.save(new Task(
            p1, "Build React Kanban Board & Task Manager",
            "Interactive columns with instant status switcher, tag chips, and priority badges.",
            TaskStatus.IN_PROGRESS, Priority.HIGH, LocalDate.now().plusDays(2)
        ));

        Task t4 = taskRepository.save(new Task(
            p1, "Integrate Resource Manager with Search & Tagging",
            "Allow filtering bookmarks by YouTube, GitHub, Docs, and custom tag pills.",
            TaskStatus.TODO, Priority.MEDIUM, LocalDate.now().plusDays(5)
        ));

        Task t5 = taskRepository.save(new Task(
            p2, "Benchmark LMAX Disruptor ring buffer throughput",
            "Evaluate latency under 500k messages per second on multi-core CPU.",
            TaskStatus.TODO, Priority.HIGH, LocalDate.now().plusDays(10)
        ));

        Task t6 = taskRepository.save(new Task(
            p2, "Implement FIX protocol parser & socket listener",
            "Financial Information eXchange message parser with zero-allocation buffers.",
            TaskStatus.TODO, Priority.MEDIUM, LocalDate.now().plusDays(15)
        ));

        Task t7 = taskRepository.save(new Task(
            p3, "Implement Raft leader election and log replication",
            "Handle heartbeat timers, term increments, and commit index advancement.",
            TaskStatus.DONE, Priority.URGENT, LocalDate.now().minusDays(14)
        ));

        Task t8 = taskRepository.save(new Task(
            p3, "Write comprehensive Jepsen partition tests",
            "Verify linearizable read/writes under asymmetric network splits.",
            TaskStatus.DONE, Priority.HIGH, LocalDate.now().minusDays(7)
        ));

        // 5. Create Resources
        Resource r1 = new Resource(
            demoUser, t2,
            "Spring Security 6 Architecture & JWT Guide",
            "https://docs.spring.io/spring-security/reference/servlet/architecture.html",
            ResourceType.DOCUMENTATION,
            "Essential reference for security filter chain execution order and bearer auth."
        );
        r1.setTags(new HashSet<>(Arrays.asList(tagSpring, tagSecurity, tagJava)));
        resourceRepository.save(r1);

        Resource r2 = new Resource(
            demoUser, t3,
            "React 19 & Tailwind CSS UI Patterns",
            "https://react.dev/reference/react",
            ResourceType.DOCUMENTATION,
            "Component hierarchy, state management with hooks, and accessible keyboard navigation."
        );
        r2.setTags(new HashSet<>(Arrays.asList(tagReact)));
        resourceRepository.save(r2);

        Resource r3 = new Resource(
            demoUser, t7,
            "The Raft Consensus Algorithm - Interactive Paper",
            "https://raft.github.io/",
            ResourceType.ARTICLE,
            "Visual step-by-step simulator showing split votes, term convergence, and log commits."
        );
        r3.setTags(new HashSet<>(Arrays.asList(tagSysDesign)));
        resourceRepository.save(r3);

        Resource r4 = new Resource(
            demoUser, null,
            "System Design Primer - Distributed Systems & Caching",
            "https://github.com/donnemartin/system-design-primer",
            ResourceType.GITHUB,
            "Top open-source reference for scalability, database sharding, and caching strategies."
        );
        r4.setTags(new HashSet<>(Arrays.asList(tagSysDesign, tagDatabase)));
        resourceRepository.save(r4);

        Resource r5 = new Resource(
            demoUser, t2,
            "Full Spring Boot 3 + JWT Authentication Crash Course",
            "https://www.youtube.com/watch?v=KxqlJblhzfI",
            ResourceType.YOUTUBE,
            "Great walkthrough for stateless token generation, claims extraction, and filter chains."
        );
        r5.setTags(new HashSet<>(Arrays.asList(tagSpring, tagSecurity, tagJava)));
        resourceRepository.save(r5);

        Resource r6 = new Resource(
            demoUser, null,
            "PostgreSQL Indexing: How GIN, B-Tree and Hash Indexes Work",
            "https://use-the-index-luke.com/",
            ResourceType.ARTICLE,
            "Deep dive into multi-column indexes, query execution plans, and EXPLAIN ANALYZE."
        );
        r6.setTags(new HashSet<>(Arrays.asList(tagDatabase)));
        resourceRepository.save(r6);

        logger.info("Demo data seeding completed successfully!");
    }
}
