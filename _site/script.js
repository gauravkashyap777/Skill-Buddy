/* =========================================================
   SKILL BUDDY
   Main JavaScript — with backend auth wired
========================================================= */


/* =========================================================
   STORAGE KEYS
========================================================= */

const USERS_KEY = "skillBuddyUsers";

const CURRENT_USER_KEY = "skillBuddyUser";

const TOKEN_KEY = "skillBuddyToken";


/* =========================================================
   API HELPERS
========================================================= */

function getToken() {
    return localStorage.getItem(TOKEN_KEY);
}

function saveToken(token) {
    if (token) {
        localStorage.setItem(TOKEN_KEY, token);
    }
}

function clearToken() {
    localStorage.removeItem(TOKEN_KEY);
}

/*
 * apiFetch — thin wrapper around fetch()
 *
 *  - Always sends JSON
 *  - Adds "Authorization: Bearer <token>" when a token exists
 *  - Parses JSON response (safe if empty)
 *  - Throws an Error with the server's message on non-2xx
 */
async function apiFetch(path, options = {}) {

    const headers = Object.assign(
        { "Content-Type": "application/json" },
        options.headers || {}
    );

    const token = getToken();

    if (token) {
        headers["Authorization"] = "Bearer " + token;
    }

    const res = await fetch(path, {
        ...options,
        headers
    });

    let body = null;

    try {
        body = await res.json();
    } catch (e) {
        body = null;
    }

    if (!res.ok) {

        /* 401 = token expired/invalid → force logout */
        if (res.status === 401) {
            clearToken();
        }

        const msg =
            (body && (body.message || body.error)) ||
            ("Request failed: " + res.status);

        throw new Error(msg);
    }

    return body;
}


/*
 * loadCurrentUserFromServer — validates the stored token
 * by calling /api/auth/me. If it succeeds, refreshes the
 * cached user in localStorage. If not, returns null.
 */
async function loadCurrentUserFromServer() {

    if (!getToken()) {
        return null;
    }

    try {

        const data = await apiFetch("/api/auth/me");

        const user = {
            id: data.id,
            username: data.username,
            email: data.email,
            role: data.role
        };

        saveCurrentUser(user);

        return user;

    } catch (e) {

        clearToken();
        return null;

    }

}


/* =========================================================
   SAMPLE SKILLS
========================================================= */

const skillData = [

    "Java",
    "Python",
    "JavaScript",
    "HTML",
    "CSS",
    "React",
    "Spring Boot",
    "SQL",
    "DSA",
    "Machine Learning",
    "Data Science",
    "Android",
    "Web Development"

];


/* =========================================================
   MENTOR DATA
========================================================= */

const mentorData = [

    {
        name: "Ananya Sharma",
        skills: ["Java", "Spring Boot"],
        rating: 4.9
    },

    {
        name: "Rahul Verma",
        skills: ["Python", "Machine Learning"],
        rating: 4.8
    },

    {
        name: "Priya Singh",
        skills: ["Web Development", "JavaScript"],
        rating: 4.9
    },

    {
        name: "Arjun Mehta",
        skills: ["DSA", "Java"],
        rating: 4.8
    },

    {
        name: "Neha Gupta",
        skills: ["Data Science", "Python"],
        rating: 4.9
    },

    {
        name: "Vikash Kumar",
        skills: ["React", "JavaScript"],
        rating: 4.7
    }

];


/* =========================================================
   COURSE DATA
========================================================= */

const courseData = [

    {
        id: "java",
        title: "Java Programming",
        category: "Programming",
        level: "Beginner to Advanced",
        description:
            "Learn Java programming from basics to advanced concepts.",
        modules: [
            "Introduction to Java",
            "Variables and Data Types",
            "Operators",
            "Conditional Statements",
            "Loops",
            "Arrays",
            "Methods",
            "OOPs",
            "Inheritance",
            "Polymorphism",
            "Exception Handling",
            "Collections",
            "File Handling",
            "Multithreading"
        ]
    },

    {
        id: "dsa",
        title: "Data Structures & Algorithms",
        category: "DSA",
        level: "Intermediate",
        description:
            "Learn data structures and algorithms with coding practice.",
        modules: [
            "Time Complexity",
            "Arrays",
            "Strings",
            "Linked List",
            "Stack",
            "Queue",
            "Recursion",
            "Binary Tree",
            "Binary Search Tree",
            "Heap",
            "Hashing",
            "Sorting",
            "Searching",
            "Graphs"
        ]
    },

    {
        id: "spring",
        title: "Spring Boot",
        category: "Backend Development",
        level: "Intermediate",
        description:
            "Build backend applications and REST APIs using Spring Boot.",
        modules: [
            "Spring Introduction",
            "Spring Boot Setup",
            "Dependency Injection",
            "IoC",
            "Spring Beans",
            "REST API",
            "Controllers",
            "Services",
            "Repositories",
            "MySQL Integration",
            "JPA",
            "Hibernate",
            "Authentication"
        ]
    },

    {
        id: "web",
        title: "Web Development",
        category: "Web Development",
        level: "Beginner to Advanced",
        description:
            "Learn HTML, CSS and JavaScript to build modern websites.",
        modules: [
            "HTML Basics",
            "HTML Forms",
            "CSS Basics",
            "Flexbox",
            "Grid",
            "Responsive Design",
            "JavaScript Basics",
            "DOM",
            "Events",
            "Fetch API",
            "Projects"
        ]
    },

    {
        id: "python",
        title: "Python & Machine Learning",
        category: "Machine Learning",
        level: "Intermediate",
        description:
            "Learn Python and the fundamentals of machine learning.",
        modules: [
            "Python Basics",
            "Functions",
            "Lists and Dictionaries",
            "OOPs in Python",
            "NumPy",
            "Pandas",
            "Matplotlib",
            "Data Preprocessing",
            "Machine Learning Basics",
            "Regression",
            "Classification"
        ]
    },

    {
        id: "data-science",
        title: "Data Science",
        category: "Data Science",
        level: "Intermediate",
        description:
            "Learn data analysis, visualization and data science concepts.",
        modules: [
            "Introduction to Data Science",
            "Python for Data Science",
            "NumPy",
            "Pandas",
            "Data Cleaning",
            "Data Visualization",
            "Statistics",
            "Exploratory Data Analysis",
            "Machine Learning Basics",
            "Projects"
        ]
    }

];


/* =========================================================
   PER-USER DATA KEYS
========================================================= */

const USER_DATA_KEYS = [

    "skillBuddyLearnSkills",
    "skillBuddyTeachSkills",
    "skillBuddySkills",
    "skillBuddyCurrentLearningSkill",
    "skillBuddyMentor",
    "skillBuddySelectedBuddy",
    "skillBuddyUpcomingSession",
    "skillBuddyScore",
    "skillBuddyAssessmentResult",
    "skillBuddyAssessmentPassed",
    "skillBuddyCourseCompleted",
    "skillBuddyCompletedCourse",
    "skillBuddyCompletedModules",
    "skillBuddyFeedback",
    "skillBuddyFeedbackSubmitted",
    "skillBuddyCertificateId",
    "skillBuddyPoints",
    "skillBuddyRole",
    "skillBuddyPreferredTime",
    "skillBuddyLearningMode",
    "skillBuddySelectedSkills",
    "skillBuddyTeachingOffer",
    "skillBuddySkillRequest",
    "skillBuddyExchangePartner",
    "skillBuddyAssessmentBySkill",
    "skillBuddyCompletedCourses"

];


/* =========================================================
   GET USERS (still used as a cache for non-auth flows)
========================================================= */

function getUsers() {

    const users =
        localStorage.getItem(USERS_KEY);

    if (!users) {
        return [];
    }

    try {

        const parsedUsers =
            JSON.parse(users);

        return Array.isArray(parsedUsers)
            ? parsedUsers
            : [];

    } catch (error) {

        console.error(
            "Unable to read users:",
            error
        );

        return [];

    }

}


/* =========================================================
   SAVE USERS
========================================================= */

function saveUsers(users) {

    localStorage.setItem(
        USERS_KEY,
        JSON.stringify(users)
    );

}


/* =========================================================
   GET CURRENT USER
========================================================= */

function getCurrentUser() {

    const user =
        localStorage.getItem(
            CURRENT_USER_KEY
        ) ||
        sessionStorage.getItem(
            CURRENT_USER_KEY
        );

    if (!user) {
        return null;
    }

    try {

        return JSON.parse(user);

    } catch (error) {

        console.error(
            "Unable to read current user:",
            error
        );

        return null;

    }

}


/* =========================================================
   SAVE CURRENT USER
========================================================= */

function saveCurrentUser(user) {

    const json =
        JSON.stringify(user);

    localStorage.setItem(
        CURRENT_USER_KEY,
        json
    );

    sessionStorage.setItem(
        CURRENT_USER_KEY,
        json
    );

}


/* =========================================================
   PER-USER DATA BACKUP / RESTORE
========================================================= */

function snapshotCurrentUserData() {

    const snapshot = {};

    USER_DATA_KEYS.forEach(key => {

        const val = localStorage.getItem(key);

        if (val !== null) {
            snapshot[key] = val;
        }

    });

    Object.keys(localStorage).forEach(key => {

        if (key.startsWith("skillBuddyProgress_")) {
            snapshot[key] = localStorage.getItem(key);
        }

    });

    return snapshot;

}


function clearUserData() {

    USER_DATA_KEYS.forEach(key => {

        localStorage.removeItem(key);

    });

    Object.keys(localStorage).forEach(key => {

        if (key.startsWith("skillBuddyProgress_")) {
            localStorage.removeItem(key);
        }

    });

}


function restoreUserData(snapshot) {

    clearUserData();

    Object.keys(snapshot).forEach(key => {

        localStorage.setItem(key, snapshot[key]);

    });

}


function saveCurrentUserSnapshot(userId) {

    if (!userId) return;

    const snapshot = snapshotCurrentUserData();

    localStorage.setItem(
        "skillBuddyUserData_" + userId,
        JSON.stringify(snapshot)
    );

}


function switchUser(newUser) {

    if (!newUser || !newUser.id) return;

    let previousUserId = null;

    try {

        const raw =
            localStorage.getItem(
                "skillBuddyLastLoggedUser"
            );

        if (raw) {
            previousUserId =
                JSON.parse(raw).id || null;
        }

    } catch (e) {

        previousUserId = null;

    }

    if (previousUserId === newUser.id) {

        console.log(
            "✅ Same user — data preserved"
        );

        return;

    }

    if (previousUserId) {

        saveCurrentUserSnapshot(previousUserId);

        console.log(
            "💾 Saved data for user " +
            previousUserId
        );

    }

    const savedRaw =
        localStorage.getItem(
            "skillBuddyUserData_" + newUser.id
        );

    if (savedRaw) {

        try {

            const snapshot =
                JSON.parse(savedRaw);

            restoreUserData(snapshot);

            console.log(
                "✅ Restored data for user " +
                newUser.id
            );

        } catch (e) {

            clearUserData();

            console.log(
                "🆕 Fresh start (parse error)"
            );

        }

    } else {

        clearUserData();

        console.log(
            "🆕 Fresh start for new user " +
            newUser.id
        );

    }

    localStorage.setItem(
        "skillBuddyLastLoggedUser",
        JSON.stringify({
            id: newUser.id,
            username: newUser.username
        })
    );

}


/* =========================================================
   LOGOUT
========================================================= */

function logoutUser() {

    try {

        const currentRaw =
            localStorage.getItem(
                CURRENT_USER_KEY
            ) ||
            sessionStorage.getItem(
                CURRENT_USER_KEY
            );

        if (currentRaw) {

            const currentUser =
                JSON.parse(currentRaw);

            if (currentUser && currentUser.id) {

                saveCurrentUserSnapshot(
                    currentUser.id
                );

            }

        }

    } catch (e) {

        console.error(
            "Logout save failed:",
            e
        );

    }

    sessionStorage.removeItem(
        CURRENT_USER_KEY
    );

    localStorage.removeItem(
        CURRENT_USER_KEY
    );

    localStorage.removeItem(
        "skillBuddyUser"
    );

    localStorage.removeItem(
        "skillBuddySelectedSkills"
    );

    clearToken();                       /* ← clears JWT */

    window.location.href =
        "index.html";

}


/* =========================================================
   LOGIN MODAL
========================================================= */

function openLoginModal() {

    const modal =
        document.getElementById(
            "loginModal"
        );

    if (!modal) return;

    modal.classList.add("active");

    document.body.style.overflow =
        "hidden";

}


function closeLoginModal() {

    const modal =
        document.getElementById(
            "loginModal"
        );

    if (!modal) return;

    modal.classList.remove("active");

    document.body.style.overflow =
        "";

}


/* =========================================================
   REGISTER MODAL
========================================================= */

function openRegisterModal() {

    const modal =
        document.getElementById(
            "registerModal"
        );

    if (!modal) return;

    modal.classList.add("active");

    document.body.style.overflow =
        "hidden";

}


function closeRegisterModal() {

    const modal =
        document.getElementById(
            "registerModal"
        );

    if (!modal) return;

    modal.classList.remove("active");

    document.body.style.overflow =
        "";

}


/* =========================================================
   SWITCH LOGIN / REGISTER
========================================================= */

function switchToRegister() {

    closeLoginModal();
    openRegisterModal();

}


function switchToLogin() {

    closeRegisterModal();
    openLoginModal();

}


/* =========================================================
   NOTIFICATION
========================================================= */

function showNotification(
    message,
    type = "success"
) {

    const oldNotification =
        document.querySelector(
            ".notification"
        );

    if (oldNotification) {
        oldNotification.remove();
    }

    const notification =
        document.createElement("div");

    notification.className =
        `notification ${type}`;

    notification.textContent =
        message;

    document.body.appendChild(
        notification
    );

    setTimeout(() => {

        if (notification) {
            notification.remove();
        }

    }, 3000);

}


/* =========================================================
   REGISTER — now calls backend /api/auth/register
========================================================= */

async function registerUser(event) {

    event.preventDefault();

    const username =
        document.getElementById(
            "registerUsername"
        ).value.trim();

    const email =
        document.getElementById(
            "registerEmail"
        ).value.trim().toLowerCase();

    const password =
        document.getElementById(
            "registerPassword"
        ).value;

    const confirmPassword =
        document.getElementById(
            "registerConfirmPassword"
        ).value;

    const roleEl =
        document.getElementById("userRole");

    const role =
        roleEl ? roleEl.value : "student";

    const terms =
        document.getElementById("terms");

    if (username.length < 3) {

        showNotification(
            "Username must contain at least 3 characters.",
            "error"
        );

        return;

    }

    if (password.length < 6) {

        showNotification(
            "Password must contain at least 6 characters.",
            "error"
        );

        return;

    }

    if (password !== confirmPassword) {

        showNotification(
            "Passwords do not match.",
            "error"
        );

        return;

    }

    if (!terms || !terms.checked) {

        showNotification(
            "Please accept the terms and conditions.",
            "error"
        );

        return;

    }

    try {

        const data = await apiFetch(
            "/api/auth/register",
            {
                method: "POST",
                body: JSON.stringify({
                    username,
                    email,
                    password,
                    role
                })
            }
        );

        saveToken(data.token);

        const user = {
            id: data.id,
            username: data.username,
            email: data.email,
            role: data.role
        };

        switchUser(user);
        saveCurrentUser(user);

        const registerForm =
            document.getElementById(
                "registerForm"
            );

        if (registerForm) {
            registerForm.reset();
        }

        closeRegisterModal();

        showNotification(
            "Account created successfully! Redirecting...",
            "success"
        );

        setTimeout(() => {

            window.location.href =
                "select-skills.html";

        }, 1200);

    } catch (e) {

        showNotification(
            e.message || "Registration failed",
            "error"
        );

    }

}


/* =========================================================
   LOGIN — now calls backend /api/auth/login
========================================================= */

async function loginUser(event) {

    event.preventDefault();

    const username =
        document.getElementById(
            "loginUsername"
        ).value.trim();

    const emailEl =
        document.getElementById("loginEmail");

    const email =
        emailEl ? emailEl.value.trim().toLowerCase() : "";

    const password =
        document.getElementById(
            "loginPassword"
        ).value;

    const rememberEl =
        document.getElementById("rememberMe");

    const rememberMe =
        rememberEl ? rememberEl.checked : false;

    if (!username && !email) {

        showNotification(
            "Please enter username or email.",
            "error"
        );

        return;

    }

    if (!password) {

        showNotification(
            "Please enter password.",
            "error"
        );

        return;

    }

    try {

        const data = await apiFetch(
            "/api/auth/login",
            {
                method: "POST",
                body: JSON.stringify({
                    username,
                    email,
                    password
                })
            }
        );

        saveToken(data.token);

        const user = {
            id: data.id,
            username: data.username,
            email: data.email,
            role: data.role
        };

        switchUser(user);
        saveCurrentUser(user);

        if (rememberMe) {

            localStorage.setItem(
                "skillBuddyRememberMe",
                "true"
            );

        } else {

            localStorage.removeItem(
                "skillBuddyRememberMe"
            );

        }

        const loginForm =
            document.getElementById(
                "loginForm"
            );

        if (loginForm) {
            loginForm.reset();
        }

        closeLoginModal();

        showNotification(
            `Welcome back, ${user.username}!`,
            "success"
        );

        setTimeout(() => {

            window.location.href =
                "select-skills.html";

        }, 1200);

    } catch (e) {

        showNotification(
            e.message || "Invalid credentials.",
            "error"
        );

    }

}


/* =========================================================
   UPDATE USER (local cache only — non-auth features)
========================================================= */

function updateCurrentUser(updatedUser) {

    saveCurrentUser(updatedUser);

    const users = getUsers();

    const index =
        users.findIndex(
            user => user.id === updatedUser.id
        );

    if (index !== -1) {

        users[index] = updatedUser;
        saveUsers(users);

    }

}


/* =========================================================
   SAVE SELECTED SKILLS
   (still localStorage-only until SkillController exists)
========================================================= */

function saveSelectedSkills(
    learningSkills,
    teachingSkills
) {

    const user = getCurrentUser();

    if (!user) {

        window.location.href =
            "index.html";

        return false;

    }

    user.selectedSkills = [
        ...new Set([
            ...learningSkills,
            ...teachingSkills
        ])
    ];

    user.learningSkills = learningSkills;
    user.teachingSkills = teachingSkills;

    updateCurrentUser(user);

    localStorage.setItem(
        "skillBuddySelectedSkills",
        JSON.stringify({
            learningSkills,
            teachingSkills
        })
    );

    return true;

}


/* =========================================================
   GET DATA GETTERS
========================================================= */

function getSkillData() { return skillData; }
function getMentorData() { return mentorData; }
function getCourseData() { return courseData; }

function getCourseById(id) {

    return courseData.find(
        course => course.id === id
    );

}


/* =========================================================
   COURSE PROGRESS (localStorage only)
========================================================= */

function updateCourseProgress(courseId, progress) {

    const user = getCurrentUser();

    if (!user) return;

    if (!user.progress) {
        user.progress = {};
    }

    user.progress[courseId] = progress;

    updateCurrentUser(user);

}


function completeCourse(courseId) {

    const user = getCurrentUser();

    if (!user) return;

    if (!user.completedCourses) {
        user.completedCourses = [];
    }

    if (!user.completedCourses.includes(courseId)) {
        user.completedCourses.push(courseId);
    }

    updateCourseProgress(courseId, 100);
    updateCurrentUser(user);

}


/* =========================================================
   CERTIFICATE (localStorage only)
========================================================= */

function addCertificate(courseId) {

    const user = getCurrentUser();

    if (!user) return null;

    if (!user.certificates) {
        user.certificates = [];
    }

    const course = getCourseById(courseId);

    if (!course) return null;

    const existing =
        user.certificates.find(
            certificate =>
                certificate.courseId === courseId
        );

    if (existing) return existing;

    const certificate = {

        id: "CERT-" + Date.now(),

        courseId: courseId,

        courseName: course.title,

        studentName: user.username,

        issuedAt: new Date().toISOString()

    };

    user.certificates.push(certificate);

    updateCurrentUser(user);

    return certificate;

}


/* =========================================================
   PAGE PROTECTION
========================================================= */

function requireLogin() {

    const user = getCurrentUser();

    if (!user) {

        window.location.href =
            "index.html";

        return null;

    }

    return user;

}


/* =========================================================
   GLOBAL EVENTS
========================================================= */

document.addEventListener(
    "keydown",
    function (event) {

        if (event.key !== "Escape") {
            return;
        }

        closeLoginModal();
        closeRegisterModal();

    }
);


document.addEventListener(
    "DOMContentLoaded",
    async function () {

        const loginForm =
            document.getElementById(
                "loginForm"
            );

        const registerForm =
            document.getElementById(
                "registerForm"
            );

        if (loginForm) {

            loginForm.addEventListener(
                "submit",
                loginUser
            );

        }

        if (registerForm) {

            registerForm.addEventListener(
                "submit",
                registerUser
            );

        }

        /* If a token exists, verify it with the server.
           This keeps the client in sync with the real DB user. */
        if (getToken()) {

            const user = await loadCurrentUserFromServer();

            if (user) {
                console.log(
                    "✅ Session restored for " +
                    user.username
                );
            }

        }

    }
);


document.addEventListener(
    "click",
    function (event) {

        if (
            event.target.classList &&
            event.target.classList.contains(
                "modal-overlay"
            )
        ) {

            closeLoginModal();
            closeRegisterModal();

        }

    }
);


/* =========================================================
   GLOBAL OBJECT
========================================================= */

window.SkillBuddy = {

    getCurrentUser,
    saveCurrentUser,
    updateCurrentUser,
    logoutUser,
    requireLogin,

    getSkillData,
    getMentorData,
    getCourseData,
    getCourseById,

    saveSelectedSkills,
    updateCourseProgress,
    completeCourse,
    addCertificate,

    showNotification,

    switchUser,
    clearUserData,
    saveCurrentUserSnapshot,

    /* new — backend-aware helpers you can call from any page */
    apiFetch,
    getToken,
    clearToken,
    loadCurrentUserFromServer

};