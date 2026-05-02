@import gateway.database.Message
@import java.util.List
@param List<Message> messages

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Messages</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        tr:hover { background-color: #f9f9f9; }
    </style>
</head>
<body>
<h2>Messages</h2>

@if(messages.isEmpty())
    <p>No messages found.</p>
@else
    <table>
        <tr>
            <th>ID</th>
            <th>Receiver</th>
            <th>Content</th>
            <th>Status</th>
            <th>Driver</th>
            <th>Created At</th>
        </tr>
        @for(var message : messages)
            <tr>
                <td>${message.getId()}</td>
                <td>${message.getReceiver()}</td>
                <td>${message.getContent()}</td>
                <td>${message.getStatus()}</td>
                <td>${message.getDriver()}</td>
                <td>${message.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}</td>
            </tr>
        @endfor
    </table>
@endif
</body>
</html>