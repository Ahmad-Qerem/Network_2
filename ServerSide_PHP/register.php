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

if (isset( $_REQUEST['Fname']) && isset( $_REQUEST['Lname']) && isset( $_REQUEST['Add'])&& isset( $_REQUEST['Email'])
    && isset( $_REQUEST['Pass'])&& isset( $_REQUEST['Photo']) && isset( $_REQUEST['ID'])) {

    $Fname = $_REQUEST['Fname'];
    $Lname = $_REQUEST['Lname'];
    $space = " ";
    $Name= $Fname.$space . $Lname;
    $Address = $_REQUEST['Add'];
    $Email = $_REQUEST['Email'];
    $Pass = $_REQUEST['Pass'];
    $Photo = $_REQUEST['Photo'];
    $ID = (int)$_REQUEST['ID'];
    $Admin = 1;
    $result = $conn->query("SELECT id FROM network2.employee where id ='$ID'");
    if($result->num_rows == 0) {
        // row not found, do stuff...
        $sql = "INSERT INTO network2.employee (id,name, address, email, password, photo,admin) VALUES ('$ID','$Name','$Address','$Email','$Pass','$Photo','$Admin')";
        $result = $conn->query($sql);
        echo "0";
    } else {
        echo "1";
    }
    //$conn->close();
}
?>