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
//echo "Connected successfully";

if (isset( $_REQUEST['ID'])) {
    $Id = (int)$_REQUEST['ID'];

    $sql = "DELETE FROM network2.employee WHERE id='$Id'";
    $conn->query($sql);
    echo "Employee deleted successfully";
}
?>