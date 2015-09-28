# Modèle de classes pour le projet CV : cv-model

## Définition

Cette librairie regroupe les classes modèle du projet CV. Chaque classe modèle représente une table dans la base de données, et donc une entité (un bean), pouvant être à la fois géré côté serveur pour des traitements/contrôles ou renvoyé côté client sous forme de JSON ou autre format.

## Principe de développement

Les DAO (ainsi que les classes modèles) sont implémentées en utilisant la librairie [jto-dao-module](http://github.com/jtouzy/jto-dao-module). 

Chaque classe possède son propre DAO. Chaque DAO possède les méthodes natives `getOne(Map<String,Object>)` et `getAll()`, qui permettent respectivement de ramener un seul enregistrement unique et tous les enregistrements. Pour les tables dont l'index unique est un numéro séquentiel, la méthode `getOne(Integer)` existe également. 

Pour ajouter des jointures ou autres fonctionnalités, d'autres méthodes doivent être créées. **On ne doit pas modifier les méthodes** `query()`, `queryCollection()` **natives du DAO**, pour prévoir toutes les requêtes possibles. Elles sont désormais par défaut notées `final`.

Par convention, une requête simple sur laquelle on veut ajouter des données supplémentaires pendant la recherche, doit se nommer `getOneWithDetails()`. Pour ramener des enregistrements spécifiques, le nom de la méthode sera plutôt orienté pour représenter vraiment ce qu'elle ramène (par exemple : `getOneWithTeamAndMatchs()` pour l'objet Championship, indique qu'on veut ramener un seul championnat contenant les matchs et les équipes de ce dernier). Si on veut préciser une manière de rechercher les enregistrements dans la méthode, la convention sera plutôt de nommer le critère de recherche (par exemple : `getAllByChampionship()` lorsqu'on veut rechercher toutes les données de l'objet `ChampionshipTeam` mais uniquement pour un championnat).