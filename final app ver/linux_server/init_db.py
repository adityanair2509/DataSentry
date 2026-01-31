#!/usr/bin/env python3
import os
import sqlite3

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DB_PATH = os.path.join(BASE_DIR, 'dns_analysis.db')


def ensure_database(db_path: str = DB_PATH):
    conn = sqlite3.connect(db_path)
    cursor = conn.cursor()

    cursor.execute('''
        CREATE TABLE IF NOT EXISTS dns_queries (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            timestamp INTEGER,
            domain TEXT,
            app_name TEXT,
            source_ip TEXT,
            dest_ip TEXT,
            protocol TEXT,
            size_bytes INTEGER,
            is_risk INTEGER DEFAULT 0,
            risk_score INTEGER DEFAULT 0,
            risk_reasons TEXT,
            analysis_timestamp INTEGER,
            analysis_engine TEXT,
            device_id TEXT
        )
    ''')

    cursor.execute('''
        CREATE TABLE IF NOT EXISTS encrypted_data_log (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            device_id TEXT NOT NULL,
            timestamp INTEGER NOT NULL,
            entry_count INTEGER NOT NULL,
            encryption_version TEXT,
            processed_at INTEGER DEFAULT CURRENT_TIMESTAMP,
            success BOOLEAN DEFAULT TRUE,
            error_message TEXT
        )
    ''')

    conn.commit()
    conn.close()


if __name__ == '__main__':
    ensure_database()
    print(f"OK: {DB_PATH}")
