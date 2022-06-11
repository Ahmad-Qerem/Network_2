<?php
$servername = "localhost";
$username = "root";
$password = "";

// Create connection
$conn = new mysqli($servername, $username, $password);
$db = new mysqli('localhost', 'root', '', 'network2');

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$sql = "SELECT * FROM network2.employee";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        echo $row["id"],"$$";
        echo $row["name"],"$$";
        echo $row["email"],"$$";
        echo $row["address"],"$$";
        echo $row["email"],"$$";
        echo $row["photo"],"^^";
    }
}
?>