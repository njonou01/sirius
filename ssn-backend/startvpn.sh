#!/bin/bash

# Chemin vers le fichier de configuration OpenVPN
CONFIG_FILE="/home/gaby/EpisenCreteil.ovpn"

# Nom d'utilisateur et mot de passe
USERNAME="I1-138"
PASSWORD="hlYjAAWO"

# Vérifier si le fichier de configuration OpenVPN existe
if [ ! -f "$CONFIG_FILE" ]; then
    echo "Le fichier de configuration OpenVPN n'existe pas."
    exit 1
fi

# Vérifier si OpenVPN est installé
if ! command -v openvpn &> /dev/null; then
    echo "OpenVPN n'est pas installé sur ce système."
    exit 1
fi

# Créer un fichier temporaire pour stocker les identifiants
CREDENTIALS_FILE=$(mktemp)
echo -e "$USERNAME\n$PASSWORD" > "$CREDENTIALS_FILE"

# Se connecter au VPN
echo "Connexion au VPN en cours..."
sudo openvpn --config "$CONFIG_FILE" --auth-user-pass "$CREDENTIALS_FILE"

# Supprimer le fichier temporaire
rm -f "$CREDENTIALS_FILE"

# Vérifier le code de sortie
if [ $? -eq 0 ]; then
    echo "Connecté au VPN avec succès."
else
    echo "Erreur lors de la connexion au VPN."
fi
