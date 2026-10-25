Compte rendu — TP Android : Quiz en images

Étudiant(e) : Anouar Dario Aissaoui

Thème choisi : Les formes géométriques

Technologies : Kotlin et XML

Activité : MainActivity

1. Présentation du projet

L'application est un petit quiz de 5 questions sur les formes géométriques. À chaque question, l'utilisateur voit une image et choisit la bonne réponse parmi trois boutons. L'appli vérifie la réponse, calcule le score, et à la fin elle affiche le résultat final avec la possibilité de recommencer.

2. Interface et widgets utilisés

L'interface est faite dans le fichier activity_main.xml en utilisant une seule activité (pas de Jetpack Compose). On y trouve :

Des TextView pour les titres, le score, les numéros et les textes ;

Une ImageView pour afficher l'image sans déformation (fitCenter) ;

Une ProgressBar horizontale pour suivre l'avancement ;

Trois Button pour les choix ;

Un bouton suivant et un bouton Rejouer ;

Un ScrollView pour que ça marche bien sur les petits écrans.

3. Représentation des questions

J'ai créé une data class Question pour stocker l'énoncé, l'image (Int), les 3 choix (Array<String>), l'indice de la bonne réponse et une description pour l'accessibilité.

Les 5 questions sont stockées dans un tableau Kotlin. Les images sont toutes locales dans le dossier res/drawable (triangle, carré, cercle, étoile, rectangle). Pas de base de données ni d'internet.

4. Gestion des événements, du score et de la progression

Les boutons sont parcourus avec un forEachIndexed. Quand l'utilisateur clique, checkAnswer(index) vérifie si c'est correct :

Si c'est juste, le score augmente de 1.

Si c'est faux, le score ne bouge pas.

Une variable bloque les clics multiples et désactive les boutons après le choix.

La progression augmente à chaque réponse (juste ou fausse). Le bouton suivant reste bloqué tant qu'on ne répond pas. À la fin de la 5ème question, les choix disparaissent et le score s'affiche. Le bouton Rejouer remet tout à zéro.

5. Tests à réaliser

Test

Résultat attendu

Lancer une partie

Question 1, score 0/5, 3 choix affichés

Répondre juste

Score +1, progression +1

Répondre faux

Score inchangé, progression +1

Cliquer plusieurs fois

Boutons désactivés, pas de points en plus

Question suivante

Nouvelle image, score gardé

Fin du quiz

Score final affiché (ex: 5/5 ou 0/5)

Rejouer

Retour au début, tout à 0

Petit écran

Scroll vertical fluide

Résultats réels des tests : (À compléter)

