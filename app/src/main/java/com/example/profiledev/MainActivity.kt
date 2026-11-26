package com.example.profiledev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profiledev.ui.theme.ProfileDevTheme

data class ProjectUi(
    val id: Int,
    val title: String,
    val description: String,
    val technologies: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfileDevTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProfileScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(
    isAvailable: Boolean = true,
    modifier: Modifier = Modifier
) {
    ProfileCard(
        name = "Elsa Letellier",
        role = "Développeuse Logicielle",
        description = "Étudiante en 4ème année de Master Informatique, spécialité Architecture Logicielle, je " +
                "recherche une alternance pour mettre en pratique mes compétences en conception et " +
                "développement logiciel.",
        skills = listOf("NodeJs + React", "Java", "Anglais", "Kotlin", "Jetpack Compose", "Flutter"),
        projects = listOf(
            ProjectUi(
                id = 1,
                title = "Product Explorer",
                description = "Application Android de catalogue de produits avec écran d'accueil, détail et liste.",
                technologies = "Kotlin • Jetpack Compose"
            ),
            ProjectUi(
                id = 2,
                title = "Profil développeur",
                description = "Première fiche profil avec des composables réutilisables et des previews.",
                technologies = "Kotlin • Jetpack Compose"
            ),
            ProjectUi(
                id = 3,
                title = "Application Flutter",
                description = "Application mobile multiplateforme réalisée en TP.",
                technologies = "Dart • Flutter"
            )
        ),
        email = "elsa.letellier@email.com",
        isAvailable = isAvailable,
        onContactClick = {
            // Le clic ne fait rien pour l'instant.
        },
        onAvailabilityClick = {
            // Le clic ne fait rien pour l'instant.
        },
        // Padding externe : le parent place la fiche dans l'écran.
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    )
}

@Composable
fun ProfileCard(
    name: String,
    role: String,
    description: String,
    skills: List<String>,
    projects: List<ProjectUi>,
    email: String,
    isAvailable: Boolean,
    onContactClick: () -> Unit,
    onAvailabilityClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        tonalElevation = 2.dp
    ) {
        // Padding interne : espace le contenu de la fiche.
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                ProfileHeader(
                    name = name,
                    role = role,
                    isAvailable = isAvailable
                )
            }
            item {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            item {
                Text(
                    text = "Compétences :",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(skills) { skill ->
                        SkillItem(skill = skill)
                    }
                }
            }
            item {
                Text(
                    text = "Projets :",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            items(
                items = projects,
                key = { project -> project.id }
            ) { project ->
                ProjectCard(project = project)
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Contact"
                    )
                    Text(
                        text = "Contact :",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            item {
                ContactField(
                    email = email,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                AvailabilityBanner(
                    isAvailable = isAvailable,
                    onClick = onAvailabilityClick
                )
            }
            item {
                ActionButton(
                    text = "Contacter",
                    onClick = onContactClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ProfileHeader(
    name: String,
    role: String,
    isAvailable: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileAvatar(name = name, isAvailable = isAvailable)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = role,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun ProfileAvatar(
    name: String,
    isAvailable: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.primary
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "Avatar de $name",
                modifier = Modifier
                    .size(80.dp)
                    .padding(12.dp)
            )
        }
        Surface(
            modifier = Modifier.align(Alignment.BottomEnd),
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Text(
                text = if (isAvailable) "Dispo" else "Indispo",
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun SkillItem(
    skill: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Text(
            text = skill,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun ProjectCard(
    project: ProjectUi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = project.title,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = project.technologies,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ContactField(
    email: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = email,
        onValueChange = {},
        readOnly = true,
        label = {
            Text(text = "Email")
        },
        modifier = modifier
    )
}

@Composable
fun AvailabilityBanner(
    isAvailable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = if (isAvailable) {
            "Disponible pour une alternance"
        } else {
            "Actuellement indisponible"
        },
        color = if (isAvailable) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.error
        },
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics {
                role = Role.Button
                contentDescription = "Disponibilité, appuyer pour en savoir plus"
            }
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp)
    )
}

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenAvailablePreview() {
    ProfileDevTheme {
        ProfileScreen(isAvailable = true)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenUnavailablePreview() {
    ProfileDevTheme {
        ProfileScreen(isAvailable = false)
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun ProfileScreenDarkPreview() {
    ProfileDevTheme(darkTheme = true) {
        ProfileScreen(isAvailable = true)
    }
}
