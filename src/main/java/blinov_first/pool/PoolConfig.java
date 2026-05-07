package blinov_first.pool;

public final class PoolConfig {

    private PoolConfig() {}

    // Database connection settings
    public static final String DB_URL =
            "jdbc:mysql://localhost:3306/phonetest?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static final String DB_USER = "Host";
    public static final String DB_PASSWORD = "mmfBEL2007";
    // Pool parameters
    public static final int MIN_SIZE = 5;
    public static final int MAX_SIZE = 20;
    public static final int TIMEOUT_MS = 30000; // 30 seconds
    public static final int VALIDATION_TIMEOUT_SEC = 5;
}