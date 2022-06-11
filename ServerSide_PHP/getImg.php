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

if (isset( $_REQUEST['ID'])) {
    $id = $_REQUEST['ID'];
    $sql = "SELECT id,photo FROM network2.employee";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            if ($row["id"] == $id){
                $filename = strval($row["photo"]);
                $images="./Images/".$filename;
                $myFile = fopen($images, "r") or die("Unable to open file!");
                $img = file_get_contents($images);
                echo $img;
            }
        }
    }
}
?>