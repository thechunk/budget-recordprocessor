package recordstorage

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.SheetsScopes
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.io.File

@Component
@Profile("!test")
open class GoogleDependenciesProvider : DependenciesProvider {
    val transport = GoogleNetHttpTransport.newTrustedTransport()!!
    val jsonFactory = JacksonFactory.getDefaultInstance()!!
}

@Component
@Profile("test")
class TestGoogleDependenciesProvider : GoogleDependenciesProvider() {
    var httpRequestInitializer: HttpRequestInitializer = getCredentials(transport, jsonFactory)

    private fun getCredentials(transport: NetHttpTransport, jsonFactory: JsonFactory): Credential {
        // Load client secrets.
        val f = File("resources/client_id.json")
        val clientSecrets = GoogleClientSecrets.load(jsonFactory, f.reader())
        val scopes = listOf(SheetsScopes.SPREADSHEETS)

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder( transport, jsonFactory, clientSecrets, scopes)
            .setAccessType("offline")
            .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

}