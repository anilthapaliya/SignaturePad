# Signature Pad

A simple Android application for drawing, signing, and painting using a custom canvas-based view. This project is an early prototype intended to demonstrate custom drawing with Canvas and View using Java and XML.

---
## Overview

Signature Pad allows users to draw freehand signatures/handwriting and basic geometric shapes such as rectangles and circles. The app preserves previously drawn content and redraws it correctly during view invalidation, configuration changes, and lifecycle events.

This project is designed to be lightweight, easy to understand, and suitable for learning or demonstration purposes.

---

## Features

- Freehand drawing (signature / handwriting)
- Rectangle drawing
- Circle drawing
- Custom View with Canvas and Paint
- Preserves and redraws previously drawn shapes
- Lightweight and easy to understand
- MVC-based structure

---

## Tech Stack

- Java
- XML
- Android Canvas API
- MVC Architecture
- Android Splash Screen API

---

## SDK Requirements

- Minimum SDK: 33
- Target SDK: 36

---

## Project Structure
    ├── ui/
    │   └── CustomDrawingView.java   # Canvas-based drawing view
    ├── model/
    │   └── Shape models (Rect, Circle, Path)
    ├── controller/
    │   └── Input and drawing logic
    ├── res/
    │   └── layouts, drawables, values
    └── MainActivity.java

---

## How It Works

- Touch events are handled inside a custom `View`
- Shapes and paths are stored as model objects
- `onDraw()` redraws all previously drawn content
- No object allocations inside `onDraw()` for better performance

---

## Planned Features

- Undo / Redo support
- Export drawing as Bitmap or image
- Save and load signatures
- Stroke customization (color, width)
- Performance optimizations for large drawings

---

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/anilthapaliya/SignaturePad.git
2. Open the project in Android Studio

3. Run on a device or emulator (API 33+)

## Author

    Anil Thapliya
    anilthapaliya@gmail.com
---
