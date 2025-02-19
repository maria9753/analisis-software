// funcion que obtiene la duración de una cancion, está en la clase Cancion
@override
duracion() {
    return duracion;
}

// funcion que obtiene la duración de un album, está en la clase Album
@override
duracion() {
    int duracionTotal = 0;

    for (Cancion c: canciones) {
        duracionTotal += c.duracion();
    }

    return duracionTotal;
}

// funcion que obtiene la duración de una playlist, está en la clase Playlist
duracionPlaylist() {
    int duracionTotal = 0;
    
    for (Cancion c: canciones) {
        duracionTotal += c.duracion();
    }

    for(Album a: albumes) {
        duracionTotal += a.duracion();
    }

    for(Playlist p: playlists) {
        duracionTotal += p.duracionPlaylist();
    }

    return duracionTotal;
}
