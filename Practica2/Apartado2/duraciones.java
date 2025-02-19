@override
duracion() {
    return duracion;
}

@override
duracion() {
    int duracionTotal = 0;

    for (Cancion c: canciones) {
        duracionTotal += c.duracion();
    }

    return duracionTotal;
}

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