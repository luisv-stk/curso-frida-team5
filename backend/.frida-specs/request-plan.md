<tasks>
  <task>
    <task_name>Persist H2 Database Data</task_name>
    <subtasks>
      <subtask>
        <id>1</id>
        <name>Switch H2 to file-based storage</name>
        <description>Modify H2 configuration to use a file-based database instead of the default in-memory mode, selecting a suitable file path and JDBC URL.</description>
        <completed>true</completed>
      </subtask>
      <subtask>
        <id>2</id>
        <name>Configure application properties for persistence</name>
        <description>Update application.properties (or application.yml) to set the H2 URL, username, password, and persistence parameters to ensure data is saved to disk.</description>
        <completed>true</completed>
      </subtask>
      <subtask>
        <id>3</id>
        <name>Set up database initialization and migrations</name>
        <description>Integrate a migration tool (e.g., Flyway or Liquibase) or configure schema initialization scripts to create and update the database schema consistently on startup.</description>
        <completed>true</completed>
      </subtask>
      <subtask>
        <id>4</id>
        <name>Test and verify data persistence</name>
        <description>Run the application, insert sample data, restart the server, and verify that the data remains available in the H2 database file.</description>
        <completed>true</completed>
      </subtask>
    </subtasks>
  </task>
</tasks>