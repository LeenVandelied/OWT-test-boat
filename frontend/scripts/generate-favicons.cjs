const fs = require('fs');
const path = require('path');
const sharp = require('sharp');
const { promisify } = require('util');

const mkdir = promisify(fs.mkdir);
const readFile = promisify(fs.readFile);
const writeFile = promisify(fs.writeFile);

const outputDir = path.resolve(__dirname, '../public/icons');

async function generateIcons() {
  try {
    // Assure que le répertoire existe
    await mkdir(outputDir, { recursive: true });
    
    const svgBuffer = await readFile(path.resolve(outputDir, 'favicon.svg'));
    
    // Génère les différentes tailles d'icônes
    const sizes = [16, 32, 48, 57, 72, 96, 120, 128, 144, 152, 180, 192, 384, 512];
    
    for (const size of sizes) {
      await sharp(svgBuffer)
        .resize(size, size)
        .png()
        .toFile(path.resolve(outputDir, `favicon-${size}x${size}.png`));
      
      console.log(`Generated favicon-${size}x${size}.png`);
    }
    
    // Crée spécifiquement l'icône pour Apple Touch
    await sharp(svgBuffer)
      .resize(180, 180)
      .png()
      .toFile(path.resolve(outputDir, 'apple-touch-icon.png'));
    
    console.log('Generated apple-touch-icon.png');
    
    // Crée le manifest.json
    const manifest = {
      name: 'Boat Manager',
      short_name: 'BoatMgr',
      icons: [
        {
          src: '/icons/favicon-192x192.png',
          sizes: '192x192',
          type: 'image/png'
        },
        {
          src: '/icons/favicon-512x512.png',
          sizes: '512x512',
          type: 'image/png'
        }
      ],
      theme_color: '#ffffff',
      background_color: '#ffffff',
      display: 'standalone'
    };
    
    await writeFile(
      path.resolve(__dirname, '../public/manifest.json'),
      JSON.stringify(manifest, null, 2),
      'utf8'
    );
    
    console.log('Generated manifest.json');
    
  } catch (err) {
    console.error('Error generating favicons:', err);
  }
}

generateIcons(); 