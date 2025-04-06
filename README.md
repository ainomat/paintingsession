In this app:

1. User opens app and sees list of painting sessions
2. User can add a new session(title, date, notes)
3. User can edit or delete a session
4. User can pick wich paint brand, color and brush was used
5. User can seach and/or filter sessions (for example show sessions with sable brush)

Entity (painting session): represents a painting session, gets saved to mysql
Repository: talks to database, save, update, delete, get sessions
Views: UI, where user sees the sessions, add new session, edit/delete session

Relations/entities:
PaintingSession | Main entity
Paint | Many-to-many - paint can be used in multiple session, many sessions can use the same paint
Brush | Many-to-one - one brush can be used in multiple sessions, one brush per session
SessionDetails | One-to-one - one session can have one detail, one detail per session

