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
    $id_ex = 0;
    $sql = "SELECT id,address,email,password,photo,name,admin FROM network2.employee";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            if ($row["id"] == $id){
                $id_ex = 1;
                echo $row["id"],"$$";
                echo $row["address"],"$$";
                echo $row["email"],"$$";
                echo $row["password"],"$$";
                echo $row["admin"],"$$";
                echo $row["photo"],"$$";
                echo $row["name"];
            }
        }
        if ($id_ex == 0)
            echo "There is no user with this ID!";
    }
}
?>